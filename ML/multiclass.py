# run this file for mutli class classification

import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.preprocessing import StandardScaler
import time
from sklearn.metrics import accuracy_score, classification_report
import numpy as np
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
sklearn.svm import SVC
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt0
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import matplotlib.pyplot as plt1
import matplotlib.pyplot as plt2
import numpy as np
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
from sklearn.svm import SVC
from sklearn.preprocessing import StandardScaler
import pandas as pd


X_train_b = pd.read_csv('/data/cs5014/P2/multi/X_train.csv', header=None)
Y_train_b = pd.read_csv('/data/cs5014/P2/multi/Y_train.csv', header=None)
X_test_b = pd.read_csv('/data/cs5014/P2/multi/X_test.csv', header=None)
X_train_train, X_train_test, Y_train_train, Y_train_test = train_test_split(X_train_b, Y_train_b, test_size=0.20, random_state=50)

# fitting the total data to svm
svm_model_init = SVC()
scaler = StandardScaler()
before = time.time()
svm_model_init.fit(scaler.fit_transform(X_train_train), Y_train_train)
after = time.time()
print(after - before)
Y_pred_init = svm_model_init.predict(scaler.transform(X_train_test))
svm_report_init = classification_report(Y_train_test, Y_pred_init, output_dict=True)
print(svm_report_init)
Y_pred_init = pd.DataFrame(Y_pred_init, columns=['label'])

# reducing the dimensions of the data
X_hist = X_train_train.loc[:,:899]
X_rgb = X_train_train.loc[:,916:964]
pca_hist = PCA()
pca_hist.fit(X_hist)
plt.plot(np.cumsum(pca_hist.explained_variance_ratio_))
plt.xlabel('Number of components for histogram')
plt.ylabel('Cumulative explained variance')
plt.show()
explained_variances = pca_hist.explained_variance_ratio_
cumulative_variances = np.cumsum(explained_variances)
threshold = 0.95
n_components_hist = np.argmax(cumulative_variances >= threshold) + 1
pca_rgb = PCA()
pca_rgb.fit(X_rgb)
plt.plot(np.cumsum(pca_rgb.explained_variance_ratio_))
plt.xlabel('Number of components for rgb')
plt.ylabel('Cumulative explained variance')
plt.show()
explained_variances = pca_rgb.explained_variance_ratio_
cumulative_variances = np.cumsum(explained_variances)
threshold = 0.95
n_components_rgb = np.argmax(cumulative_variances >= threshold) + 1

# Transform train data to reduced-dimensional space
X_hist_pca = pca_hist.transform(X_hist)[:,:n_components_hist]
X_rgb_pca = pca_rgb.transform(X_rgb)[:,:n_components_rgb]
data_reduced_X_train_b = np.concatenate((X_hist_pca, X_rgb_pca), axis=1)
# Define column names for the DataFrame
column_names = ['PCA_hist_{}'.format(i) for i in range(n_components_hist)] + ['PCA_rgb_{}'.format(i) for i in range(n_components_rgb)]

# Create a DataFrame from the numpy array, with column names
df_reduced_X_train_b = pd.DataFrame(data_reduced_X_train_b, columns=column_names)

# Transform test data to reduced-dimensional space
X_hist_test = X_train_test.loc[:,:899]
X_rgb_test = X_train_test.loc[:,916:964]
X_hist_test_pca = pca_hist.transform(X_hist_test)[:,:n_components_hist]
X_rgb_test_pca = pca_rgb.transform(X_rgb_test)[:,:n_components_rgb]
data_reduced_X_test_b = np.concatenate((X_hist_test_pca, X_rgb_test_pca), axis=1)
# Define column names for the DataFrame
column_names = ['PCA_hist_{}'.format(i) for i in range(n_components_hist)] + ['PCA_rgb_{}'.format(i) for i in range(n_components_rgb)]

# Create a DataFrame from the numpy array, with column names
df_reduced_X_test_b = pd.DataFrame(data_reduced_X_test_b, columns=column_names)

# fitting the reduced data to svm
scaler = StandardScaler()
svm = SVC()
before = time.time()
svm.fit(scaler.fit_transform(df_reduced_X_train_b), Y_train_train)
after = time.time()
print(after - before)
Y_pred_svm = svm.predict(scaler.transform(df_reduced_X_test_b))
svm_report = classification_report(Y_train_test, Y_pred_svm, output_dict=True)
print(svm_report)

# plotting the performance of svm with total and reduced data
labels = ["with total data", "with pca"]
reduced_data = [svm_report_init["macro avg"]["f1-score"], svm_report["macro avg"]["f1-score"]]
plt0.bar(labels, reduced_data)
plt0.title("Performance of svm with total and reduced data based on the macro avg f1-score")
plt0.show()

# fitting the reduced data to random forest
rd_model = RandomForestClassifier()
rd_model.fit(df_reduced_X_train_b, Y_train_train)
Y_pred = rd_model.predict(df_reduced_X_test_b)
rd_report = classification_report(Y_train_test, Y_pred, output_dict=True)
print(rd_report)

# fitting the reduced data to knn
knn = KNeighborsClassifier(n_neighbors=5)
knn.fit(df_reduced_X_train_b, Y_train_train)
Y_pred_knn = knn.predict(df_reduced_X_test_b)
knn_report = classification_report(Y_train_test, Y_pred_knn, output_dict=True)
print(knn_report)

# fitting the reduced data to gaussian naive bayes
scaler = StandardScaler()
gnb = GaussianNB()
gnb.fit(scaler.fit_transform(df_reduced_X_train_b), Y_train_train)
Y_pred_gnb = gnb.predict(scaler.transform(df_reduced_X_test_b))
gnb_report = classification_report(Y_train_test, Y_pred_gnb, output_dict=True)
print(gnb_report)


# plotting the performance of classifiers
x = ["rdf", "svm", "knn", "gnb"]
y = [rd_report["macro avg"]["f1-score"], svm_report["macro avg"]["f1-score"], knn_report["macro avg"]["f1-score"],
    gnb_report["macro avg"]["f1-score"]]
plt.bar(x, y)
plt.title("Performance of classifiers based on the macro avg f1-score")
plt.show()

x1 = ["rdf", "svm", "knn", "gnb"]
y1 = [rd_report["accuracy"], svm_report["accuracy"], knn_report["accuracy"], gnb_report["accuracy"]]
plt1.bar(x1, y1)
plt1.title("Performance of classifiers based on accuracy")
plt1.show()

x2 = ["rdf", "svm", "knn", "gnb"]
y2 = [rd_report["weighted avg"]["f1-score"], svm_report["weighted avg"]["f1-score"], knn_report["weighted avg"]["f1-score"],
    gnb_report["weighted avg"]["f1-score"]]
plt2.bar(x2, y2)
plt2.title("Performance of classifiers based on the weighted avg f1-score")
plt2.show()

# final data
X_hist_total = X_train_b.loc[:,:899]
X_rgb_total = X_train_b.loc[:,916:964]

# Fit PCA to data
pca_hist = PCA()
pca_hist.fit(X_hist_total)
pca_rgb = PCA()
pca_rgb.fit(X_rgb_total)

# Get explained variance ratios
explained_variances = pca_hist.explained_variance_ratio_

# Calculate cumulative explained variances
cumulative_variances = np.cumsum(explained_variances)

# Find number of components needed to capture specified amount of variance
threshold = 0.95
n_components_hist = np.argmax(cumulative_variances >= threshold) + 1

# Get explained variance ratios
explained_variances = pca_rgb.explained_variance_ratio_

# Calculate cumulative explained variances
cumulative_variances = np.cumsum(explained_variances)

# Find number of components needed to capture specified amount of variance
threshold = 0.95
n_components_rgb = np.argmax(cumulative_variances >= threshold) + 1

# Transform data to reduced-dimensional space
X_hist_total_pca = pca_hist.fit_transform(X_hist_total)[:,:n_components_hist]
X_rgb_total_pca = pca_rgb.fit_transform(X_rgb_total)[:,:n_components_rgb]
data_reduced_X_total_b = np.concatenate((X_hist_total_pca, X_rgb_total_pca), axis=1)
# Define column names for the DataFrame
column_names = ['PCA_hist_{}'.format(i) for i in range(n_components_hist)] + ['PCA_rgb_{}'.format(i) for i in range(n_components_rgb)]

# Create a DataFrame from the numpy array, with column names
reduced_X_total_b = pd.DataFrame(data_reduced_X_total_b, columns=column_names)

X_hist_test = X_test_b.loc[:,:899]
X_rgb_test = X_test_b.loc[:,916:964]

# Transform data to reduced-dimensional space
X_hist_test_pca = pca_hist.transform(X_hist_test)[:,:n_components_hist]
X_rgb_test_pca = pca_rgb.transform(X_rgb_test)[:,:n_components_rgb]
data_reduced_X_test_b = np.concatenate((X_hist_test_pca, X_rgb_test_pca), axis=1)

# Create a DataFrame from the numpy array, with column names
reduced_X_test_b = pd.DataFrame(data_reduced_X_test_b, columns=column_names)

# Final output
scaler = StandardScaler()
svm = SVC(C=0.1, class_weight='balanced')
svm.fit(scaler.fit_transform(reduced_X_total_b), Y_train_b)
Y_pred_final_b = svm.predict(scaler.transform(reduced_X_test_b))
Y_pred_svm_multi = pd.DataFrame(Y_pred_final_b)
Y_pred_svm_multi.to_csv('Y_test.csv', index=False, header=False)
