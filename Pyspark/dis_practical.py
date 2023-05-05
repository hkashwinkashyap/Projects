import matplotlib.pyplot as plt
import numpy as np
import plotly.graph_objects as go
import plotly.io as pio
import seaborn as sns
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.feature import StringIndexer
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.stat import Correlation
from pyspark.sql import SparkSession
from pyspark.sql import functions
from pyspark.sql.functions import col
from pyspark.sql.functions import collect_set
from pyspark.sql.functions import when, min

spark = SparkSession.builder.appName('dis_practical').getOrCreate()
df = spark.read.format('csv').load('data/data/Absence_3term201819_nat_reg_la_sch.csv', header=True,
                                         inferSchema=True)

df_spark = df.persist()

# Write CSV file with column header (column names)
df_spark.write.csv("write_file.csv", mode='overwrite', header=True)


# # ## Chart/explore the performance of regions in England from 2006-2018. Your charts and subsequent analysis in your report should answer the following questions: Are there any regions that have improved in pupil attendance over the years? Are there any regions that have worsened? Which is the overall best/worst region for pupil attendance?
def performance_analysis(df_spark):
    years = [200607, 201011, 201213, 201516, 201819]
    filtered_df_regions = df_spark.filter(
        (col("school_type") == "Total") & (col("geographic_level") == "Regional") & (col("time_period").isin(years)))
    filtered_df_regions = filtered_df_regions.select(["sess_overall_percent", "time_period", "region_name"])

    # Pivot the data to have one row per region
    pivoted = filtered_df_regions.groupBy("region_name").pivot("time_period").agg(functions.first("sess_overall_percent"))

    # Calculate the difference between the two time periods
    diffs = pivoted.select("region_name", (functions.col("200607") - functions.col("201819")).alias("difference"))

    # Convert the Spark DataFrame to a dictionary
    result_dict = diffs.rdd.collectAsMap()
    sorted_dict = sorted(result_dict.items(), key=lambda x: x[1])
    print("Best performing region over the years {}".format(sorted_dict[-1]))
    print("Worst performing region over the years {}".format(sorted_dict[0]))

    # create a figure and axis object
    fig, ax = plt.subplots()

    # plot the data using ax.scatter
    for region_name in filtered_df_regions.select('region_name').distinct().rdd.flatMap(lambda x: x).collect():
        region_data = filtered_df_regions.filter(filtered_df_regions.region_name == region_name)
        ax.plot(region_data.select('time_period').rdd.flatMap(lambda x: x).collect(),
                region_data.select('sess_overall_percent').rdd.flatMap(lambda x: x).collect(), label=region_name)
        last_x = region_data.select('time_period').rdd.flatMap(lambda x: x).collect()[-1]
        last_y = region_data.select('sess_overall_percent').rdd.flatMap(lambda x: x).collect()[-1]

    # set the axis labels and title
    ax.set_xlabel('Year')
    ax.set_ylabel('Session Overall Absence Percent')
    ax.set_title('Session Overall Absence Percent by Year and Region')

    # add a legend
    ax.legend()

    # show the plot
    plt.show()
#
# # ## correlation analysis
def correlation_analysis(df_spark):
    corr_a = df_spark.filter((col("geographic_level") == "Local authority"))
    corr_a = corr_a.select(["time_period", "sess_overall_percent", "school_type", "la_name"])

    # Convert the categorical variables to numeric variables
    indexers = [StringIndexer(inputCol=col, outputCol=col + "_index").fit(corr_a) for col in ["school_type", "la_name"]]
    indexed = corr_a
    for indexer in indexers:
        indexed = indexer.transform(indexed)

    # Vectorize the relevant columns
    assembler = VectorAssembler(inputCols=["time_period", "school_type_index", "la_name_index", "sess_overall_percent"],
                                outputCol="features")
    assembled_df = assembler.transform(indexed).select("features")

    # Compute the correlation matrix
    correlation_matrix = Correlation.corr(assembled_df, "features").head()[0]

    # Convert the correlation matrix to a NumPy array
    corr_array = np.array(correlation_matrix.toArray())

    # Get the column names
    col_names = corr_a.columns

    # Create a heatmap with column labels using Seaborn and Matplotlib
    fig, ax = plt.subplots(figsize=(10, 10))
    sns.heatmap(corr_array, annot=True, xticklabels=col_names, yticklabels=col_names, cmap="coolwarm", ax=ax)

    # Show the plot
    plt.show()

data = df_spark.filter((col("geographic_level") == "Local authority") & (col("school_type") == "Total"))
data = data.select(["time_period", "sess_overall_percent", "la_name"])
data = data.withColumn("time_period",
                       when(data["time_period"] == 200607, 2007).when(data["time_period"] == 200708, 2008).when(
                           data["time_period"] == 200809, 2009).when(data["time_period"] == 200910, 2010).when(
                           data["time_period"] == 201011, 2011).when(data["time_period"] == 201112, 2012).when(
                           data["time_period"] == 201213, 2013).when(data["time_period"] == 201314, 2014).when(
                           data["time_period"] == 201415, 2015).when(data["time_period"] == 201516, 2016).when(
                           data["time_period"] == 201617, 2017).when(data["time_period"] == 201718, 2018).when(
                           data["time_period"] == 201819, 2019).otherwise(data["time_period"]))

# Index the label column region_name
label_indexer = StringIndexer(inputCol="la_name", outputCol="label")

data = label_indexer.fit(data).transform(data)

labels_lookup = data.select(["la_name", "label"])

# Group the data by year and region, and compute the minimum value of sess_overall for each group
data = data.groupby("time_period", "label").agg(min("sess_overall_percent").alias("min_sess_overall"))

# Find the region with the least sess_overall for each year
min_sess_overall_by_year = data.groupby("time_period").agg(min("min_sess_overall").alias("min_sess_overall"))
data = data.join(min_sess_overall_by_year, on=["time_period", "min_sess_overall"])

# Select the region with the least sess_overall for each year
data = data.select("time_period", "label", "min_sess_overall").filter(
    data["min_sess_overall"] == data["min_sess_overall"])
data = data.select("time_period", "label")

# Assemble features
assembler = VectorAssembler(inputCols=["time_period"], outputCol="feature")

output = assembler.transform(data)

finalised_dataset = output.select("feature", "label")

data_train, data_test = finalised_dataset.randomSplit([0.70, 0.30], seed=50)

# Fit the logistic regression model
logreg = LogisticRegression(featuresCol="feature", labelCol="label")
model = logreg.fit(data_train)


#
# print("Accuracy of the model is {}".format(model.summary.accuracy))
#
# test_results = model.transform(data_test)
#
# print(test_results.select(["feature", "label", "prediction"]).show(truncate=False))


# ## Allow the user to search the dataset by the local authority, showing the number of pupil enrolments in each local authority by time period (year).
# – Given a list of local authorities, display in a well-formatted fashion the number of pupil enrolments in each local authority by time period (year).
def search_by_la_name(df_spark, user_input_la_names):
    filtered_df = df_spark.select(["geographic_level", "school_type", "la_name", "time_period", "enrolments"])

    # Filter the data by la_name
    la_names = user_input_la_names
    filtered_df = filtered_df.filter(
        (col("geographic_level") == "Local authority") & (col("school_type") == "Total") & (
            col("la_name").isin(la_names)))
    filtered_df = filtered_df.select(["time_period", "la_name", "enrolments"])
    filtered_df.show(truncate=False, n=2000)


# ## Allow the user to search the dataset by school type, showing the total number of pupils who were given authorised absences because of medical appointments or illness in the time period 2017-2018.
def search_by_school_type(df_spark, user_input_school_type):
    # Filter the data by school type and time period
    if user_input_school_type == 1:
        school_type = "Special"
    elif user_input_school_type == 2:
        school_type = "State-funded primary"
    elif user_input_school_type == 3:
        school_type = "State-funded secondary"
    else:
        school_type = "Total"
    year_wanted = 201718
    filtered_df = df_spark.filter(
        (col("school_type") == school_type) & (col("time_period") == year_wanted) & (
                col("geographic_level") == "National"))
    filtered_df = filtered_df.select(["sess_auth_appointments", "sess_auth_illness"])

    # Calculate the total number of pupils with authorised absences
    total_pupil_enrolments = filtered_df.select(
        (col("sess_auth_appointments") + col("sess_auth_illness")).alias("Both_med_ill"))
    total_pupil_enrolments = total_pupil_enrolments.agg({"Both_med_ill": "sum"}).collect()[0][0]
    print("---------------------------------------------------------------------")
    print(
        "Total number of pupils with authorised absences due to medical appointments or illness in {0} at\nschool type: {1}\nvalue is: {2}".format(
            year_wanted, school_type, total_pupil_enrolments)
    )
    print("---------------------------------------------------------------------")

# ## Allow a user to search for all unauthorised absences in a certain year, broken down by either region name or local authority name.
def search_unauthorised_absences(df_spark, user_input_year, user_input_grouping):
    # Filter the data by unauthorised absence and year
    year = user_input_year
    user_grouping_input = user_input_grouping
    group_by = ""
    geographic_level = ""
    if user_grouping_input == "local authority":
        group_by = "la_name"
        geographic_level = "Local authority"
    else:
        group_by = "region_name"
        geographic_level = "Regional"

    unauth_absences_df = df_spark.filter(
        (col("time_period") == year) & (col("geographic_level") == geographic_level) & (col("school_type") == "Total"))
    unauth_absences_df = unauth_absences_df.select([group_by, "sess_unauthorised"])
    print("---------------------------------------------------------------------")
    print("Results for {}".format(year))
    unauth_absences_df.show(truncate=False, n=500)


# ## List the top 3 reasons for authorised absences in each year.

# Define a function to get the top 3 reasons for a given time period
def get_top3_reasons(df, time_period):
    # Filter the data by time period
    filtered_df = df.filter(col("time_period") == time_period)

    # Group the DataFrame by reason and aggregate the counts for each reason
    reasons = filtered_df.groupBy(
        "time_period", "sess_auth_illness", "sess_auth_appointments",
        "sess_auth_religious", "sess_auth_study", "sess_auth_traveller",
        "sess_auth_holiday", "sess_auth_excluded", "sess_auth_other"
    )

    reason_counts = {
        "Illness": reasons.agg({"sess_auth_illness": "sum"}).collect()[0][9],
        "Medical Appointments": reasons.agg({"sess_auth_appointments": "sum"}).collect()[0][9],
        "Religious": reasons.agg({"sess_auth_religious": "sum"}).collect()[0][9],
        "Study": reasons.agg({"sess_auth_study": "sum"}).collect()[0][9],
        "Traveller": reasons.agg({"sess_auth_traveller": "sum"}).collect()[0][9],
        "Holiday": reasons.agg({"sess_auth_holiday": "sum"}).collect()[0][9],
        "Excluded": reasons.agg({"sess_auth_excluded": "sum"}).collect()[0][9],
        "Other": reasons.agg({"sess_auth_other": "sum"}).collect()[0][9]
    }

    top3_reasons = sorted(reason_counts.items(), key=lambda x: x[1], reverse=True)[:3]

    top3_reasons_list = []
    for key, value in top3_reasons:
        top3_reasons_list.append(str("{0} with count {1}".format(key, value)))

    return top3_reasons_list


# Specify the year for which to find the top 3 reasons
def top3_reasons(df_spark):
    # Select the relevant columns
    auth_absences_df = df_spark.select(
        ["time_period", "sess_auth_illness", "sess_auth_appointments", "sess_auth_religious", "sess_auth_study",
         "sess_auth_traveller", "sess_auth_holiday", "sess_auth_excluded", "sess_auth_other"])
    auth_absences_df = auth_absences_df.filter(
        (col("school_type") == "Total") & (col("geographic_level") == "National"))
    distinct_years = df_spark.select(collect_set("time_period")).first()[0]
    distinct_years = sorted(distinct_years)
    for year in distinct_years:
        # Find the top 3 reasons for the specified year and print the results
        top3_reasons = get_top3_reasons(auth_absences_df, year)
        print("Top 3 reasons for authorised absences in the year {0}:".format(year))
        print("\n".join(top3_reasons))
        print("---------------------------------------------------------------------")


# ## Allow a user to compare two local authorities of their choosing in a given year. Justify how you will compare and present the data.

def compare_la(df_spark, year, la_name1, la_name2):
    la_names = [la_name1, la_name2]
    year = year

    # Check if la_names are present in df_spark
    la_names_present = all(
        name in df_spark.select("la_name").distinct().rdd.flatMap(lambda x: x).collect() for name in la_names)
    distinct_years = df_spark.select(collect_set("time_period")).first()[0]
    distinct_years = sorted(distinct_years)
    year_present = year in distinct_years

    if la_names_present and year_present:
        # Filter the data by la_name and year
        filtered_df_la = df_spark.filter((col("la_name").isin(la_names)) & (col("time_period") == year) & (
                col("geographic_level") == "Local authority") & (col("school_type") == "Total"))
        # Get the enrolments and authorised/unauthorised sessions as NumPy arrays
        enrolments = filtered_df_la.select("enrolments").rdd.flatMap(lambda x: x).collect()
        authorised_sessions = filtered_df_la.select("sess_authorised").rdd.flatMap(lambda x: x).collect()
        unauthorised_sessions = filtered_df_la.select("sess_unauthorised").rdd.flatMap(lambda x: x).collect()

        # Plot a stacked bar chart
        ind = np.arange(len(la_names))
        width = 0.35
        fig, ax = plt.subplots()
        p1 = ax.bar(ind, enrolments, width, label='Enrolments')
        p2 = ax.bar(ind + width, authorised_sessions, width, label='Authorised')
        p3 = ax.bar(ind + width, unauthorised_sessions, width, bottom=authorised_sessions, label='Unauthorised')
        ax.set_ylabel("Total Sessions/Enrolments")
        ax.set_xticks(ind + width / 2)
        ax.set_xticklabels(la_names)
        ax.legend()
        ax.set_title("Comparison of {} in {}".format(", ".join(la_names), year))
        plt.show()

    else:
        print("Invalid input")


# ## visualisation
def visualisation(df_spark):
    vis_df = df_spark.filter((col("school_type") == "Total") & (col("geographic_level") == "Regional"))
    fig = go.Figure()

    fig.add_trace(go.Scatter(x=vis_df.select(col("region_name")).rdd.flatMap(lambda x: x).collect(),
                             y=vis_df.select(col("enrolments")).rdd.flatMap(lambda x: x).collect(),
                             mode="markers",
                             marker=dict(color="blue"),
                             text=[
                                 f"Authorised %: {sess_authorised_percent}<br>Total reasons auth: {sess_auth_totalreasons}<br>Total reasons unauth: {sess_unauth_totalreasons}<br>Time Period: {time_period}"
                                 for
                                 sess_authorised_percent, sess_auth_totalreasons, sess_unauth_totalreasons, time_period
                                 in zip(vis_df.select("sess_authorised_percent").rdd.flatMap(lambda x: x).collect(),
                                        vis_df.select("sess_auth_totalreasons").rdd.flatMap(lambda x: x).collect(),
                                        vis_df.select("sess_unauth_totalreasons").rdd.flatMap(lambda x: x).collect(),
                                        vis_df.select("time_period").rdd.flatMap(lambda x: x).collect())]
                             ))

    # Add a title and axis labels
    fig.update_layout(title="Enrolments region wise", xaxis_title="region_name", yaxis_title="enrolments")

    # Show the plot
    pio.show(fig, validate=True)


def get_best_local_authority(user_input_year, model):
    user_input = user_input_year

    # Create a new DataFrame with the input data
    new_data = spark.createDataFrame([(user_input,)], ["time_period"])

    # Assemble the features using the assembler object
    new_data_assembled = assembler.transform(new_data)

    # Use the logistic regression model to predict the label for the new input
    predicted_data = model.transform(new_data_assembled)

    # Display the predicted label and its corresponding la_name
    predicted_label = predicted_data.select("prediction").collect()[0][0]
    la_name_predicited = labels_lookup.filter(labels_lookup["label"] == predicted_label).select("la_name").collect()[0][
        0]
    print("---------------------------------------------------------------------")
    print("The best predicited local authority in year {} is \"{}\"".format(user_input, la_name_predicited))
    print("---------------------------------------------------------------------")


user_queries = ("Enter the following number associated with the query to execute it\n"
                "1. Search the dataset by the local authority,\n"
                "\tshowing the number of pupil enrolments in each local authority by time period (year).\n"
                "2. Search the dataset by school type, showing the total number of pupils who were given \n"
                "\tauthorised absences because of medical appointments or illness in the time period 2017-2018.\n"
                "3. Search for all unauthorised absences in a certain year,\n"
                "\tbroken down by either region name or local authority name.\n"
                "4. List the top 3 reasons for authorised absences in each year.\n"
                "5. Compare two local authorities in a given year.\n"
                "6. Visualisation of the dataset.\n"
                "7. Predict the best local authority in a given year.\n"
                "8. Performance of regions.\n"
                "9. Correlation between time_period, sess_overall_percent, school_type, la_name.\n"
                "10. Exit.\n")

while True:
    user_input = input(user_queries)
    if user_input.lower() == '10':
        quit_input = input("Do you want to exit? Press q to exit.\n")
        if quit_input == 'q':
            break
        else:
            continue
    elif user_input.lower() == '9':
        correlation_analysis(df_spark)
    elif user_input.lower() == '8':
        performance_analysis(df_spark)
    elif user_input.lower() == '7':
        go_back = False
        while not go_back:
            user_input_year = input("Enter the year to predict the best local authority:\n")
            if user_input_year.isdigit():
                go_back = True
                get_best_local_authority(int(user_input_year), model)
            else:
                print("Please enter a valid year.\n")
    elif user_input.lower() == '6':
        visualisation(df_spark)
    elif user_input.lower() == '5':
        go_back = False
        while not go_back:
            user_input_year = input("Enter the year to compare the local authorities:\n")
            user_input_la_name1 = input("Enter the first local authority name:\n")
            user_input_la_name2 = input("Enter the second local authority name:\n")
            if user_input_year.isdigit():
                go_back = True
                compare_la(df_spark, int(user_input_year), user_input_la_name1, user_input_la_name2)
            else:
                print("Please enter a valid year.\n")
    elif user_input.lower() == '4':
        top3_reasons(df_spark)
    elif user_input.lower() == '3':
        go_back = False
        while not go_back:
            user_input_year = input("Enter the year to search for unauthorised absences: ")
            user_input_la_or_region = input("Enter 1 if you want to sort by local authority name\n"
                                            "or 2 if you want to sort by region name:\n")
            if user_input_year.isdigit() and user_input_la_or_region.isdigit():
                go_back = True
                search_unauthorised_absences(df_spark, user_input_year,
                                             "local authority" if user_input_la_or_region == '1' else " ")
            else:
                print("Please enter a valid year.\n")
    elif user_input.lower() == '2':
        go_back = False
        while not go_back:
            user_input_school_type = input("Enter 1 if you want to search by Special School\n"
                                           "or 2 if you want to search by State-funded primary\n"
                                           "or 3 if you want to search by State-funded secondary\n"
                                           "or 4 if you want to search by Total\n")
            if user_input_school_type == '1' or user_input_school_type == '2' or user_input_school_type == '3' or user_input_school_type == '4':
                go_back = True
                search_by_school_type(df_spark, int(user_input_school_type))
            else:
                print("Please enter a valid number.\n")
    elif user_input.lower() == '1':
        go_back = False
        while not go_back:
            user_input_number = input("Enter how many local authority names you to search for:\n")
            if user_input_number.isdigit():
                user_input_la_names = []
                for i in range(int(user_input_number)):
                    user_input_la_name = input("Enter the local authority name:\n")
                    user_input_la_names.append(user_input_la_name)
                go_back = True
                search_by_la_name(df_spark, user_input_la_names)
            else:
                print("Please enter a valid local authority name.\n")
