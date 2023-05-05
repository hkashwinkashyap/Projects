import pandas as pd
import seaborn as sns
from matplotlib import pyplot as plt
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, balanced_accuracy_score, confusion_matrix, precision_recall_curve
from sklearn.metrics import precision_score, recall_score, f1_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import StandardScaler, PolynomialFeatures


def print_metrics(X_test, y_test, y_pred, logreg):
    # printing metrics
    print('----------------------------------------------------------------------------------')
    print('Accuracy score:                  ' + str(accuracy_score(y_test, y_pred)))
    print('Balanced accuracy score:         ' + str(balanced_accuracy_score(y_test, y_pred)))
    print('Predict proba:')
    print(logreg.predict_proba(X_test)[0:5])
    print('Decision function:')
    print(logreg.decision_function(X_test)[0:5])
    print('Confusion matrix:')
    print(confusion_matrix(y_test, y_pred))
    print('Precision score:')
    print(precision_score(y_test, y_pred, average=None))
    print('Recall score:')
    print(recall_score(y_test, y_pred, average=None))
    print('micro averages:')
    print('Precision             Recall               F1')
    print(str(precision_score(y_test, y_pred, average='micro')) + '   ' + str(
        recall_score(y_test, y_pred, average='micro')) + '   ' + str(f1_score(y_test, y_pred, average='micro')))
    print('macro averages:')
    print('Precision             Recall               F1')
    print(str(precision_score(y_test, y_pred, average='macro')) + '   ' + str(
        recall_score(y_test, y_pred, average='macro')) + '   ' + str(f1_score(y_test, y_pred, average='macro')))
    print('----------------------------------------------------------------------------------')


data = pd.read_csv('drybeans.csv')
pd.isnull(data.columns)
data.dropna(inplace=True)
duplicates = data.duplicated().sum()
print('duplicates present: ' + str(duplicates))
data.drop_duplicates(inplace=True)
print('dropped the duplicates.')
le = LabelEncoder()
print(data['Class'].unique())
data['Class'] = le.fit_transform(data['Class'])
print(data['Class'].unique())

# plotting the correlation matrix
corr = data.corr()
plt.figure(figsize=(20, 20))
sns.heatmap(corr, annot=True, cmap=plt.cm.Reds)
plt.show()

X = data.iloc[:, :-1]
y = data.iloc[:, -1]

# splitting the data into train and test
X_train_prlim, X_test_prlim, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=50)

print(y_test.shape)
# scaling the data
scaler = StandardScaler()
X_train_prlim[:] = scaler.fit_transform(X_train_prlim)
X_test_prlim[:] = scaler.transform(X_test_prlim)

# dropping features
X_train_prlim = X_train_prlim.drop(
    ['Area', 'ConvexArea', 'MajorAxisLength', 'ShapeFactor2', 'ShapeFactor3', 'ShapeFactor4',
     'AspectRation'], axis=1)
X_test_prlim = X_test_prlim.drop(
    ['Area', 'ConvexArea', 'MajorAxisLength', 'ShapeFactor2', 'ShapeFactor3', 'ShapeFactor4',
     'AspectRation'], axis=1)

X_train = X_train_prlim
X_test = X_test_prlim

logreg = LogisticRegression(penalty='none', class_weight=None, max_iter=1000)
logreg.fit(X_train, y_train)
y_pred = logreg.predict(X_test)
print('Unbalanced accuracy score:')
print_metrics(X_test, y_test, y_pred, logreg)

logreg1 = LogisticRegression(penalty='none', class_weight='balanced', max_iter=1000)
logreg1.fit(X_train, y_train)
y_pred1 = logreg1.predict(X_test)
print('Balanced accuracy score:')
print_metrics(X_test, y_test, y_pred1, logreg1)

logreg2 = LogisticRegression(penalty='l2', class_weight='balanced', max_iter=1000)
logreg2.fit(X_train, y_train)
y_pred2 = logreg2.predict(X_test)
print('l2 balanced accuracy score:')
print_metrics(X_test, y_test, y_pred2, logreg2)

# plotting the precision-recall curve for l2 balanced
precision = dict()
recall = dict()
for i in range(0, 7):
    precision[i], recall[i], _ = precision_recall_curve(y_test, logreg2.decision_function(X_test)[:, i], pos_label=i)
    plt.plot(recall[i], precision[i], label='class {}'.format(i))
plt.xlabel('Recall')
plt.ylabel('Precision')
plt.legend()
plt.show()

# polynomial features
poly = PolynomialFeatures(2)
X_poly_train = poly.fit_transform(X_train)
X_poly_test = poly.transform(X_test)

# polynomial features' stats
print('Shape of the train data:')
print('old shape :' + str(X_train.shape))
print('after the polynomial fit: ' + str(X_poly_train.shape))
print('Shape of the test data:')
print('old shape :' + str(X_test.shape))
print('after the polynomial fit:  ' + str(X_poly_test.shape))

logreg3 = LogisticRegression(penalty='none', class_weight='balanced', max_iter=1000)
logreg3.fit(X_poly_train, y_train)
y_pred3 = logreg3.predict(X_poly_test)
print('Balanced accuracy score poly:')
print_metrics(X_poly_test, y_test, y_pred3, logreg3)

logreg4 = LogisticRegression(penalty='l2', class_weight='balanced')
logreg4.fit(X_poly_train, y_train)
y_pred4 = logreg4.predict(X_poly_test)
print('Balanced accuracy score poly with l2:')
print_metrics(X_poly_test, y_test, y_pred4, logreg4)
