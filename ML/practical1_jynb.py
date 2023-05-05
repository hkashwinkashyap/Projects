#!/usr/bin/env python
# coding: utf-8

# In[34]:


import pandas as pd
import sklearn as skl
import numpy as np
data=pd.read_csv('drybeans.csv')
data['Class'].unique()


# In[35]:


pd.isnull(data.columns)


# In[36]:


#clean the data
data=data.dropna()
data['Class'].unique()


# In[37]:


#encode the data
from sklearn.preprocessing import LabelEncoder
le=LabelEncoder()
data['Class']=le.fit_transform(data['Class'])
data['Class'].unique()


# In[38]:


data


# In[39]:


#find duplicates
duplicates = data.duplicated().sum()
duplicates


# In[40]:


#remove duplicates
data=data.drop_duplicates()
data


# In[41]:


duplicates = data.duplicated().sum()
duplicates


# In[42]:


#change a column name
data=data.rename(columns={'AspectRation':'AspectRatio'})
data


# In[43]:


#normalize the data except the class column
from sklearn.preprocessing import MinMaxScaler
scaler=MinMaxScaler()
data.iloc[:,0:-1]=scaler.fit_transform(data.iloc[:,0:-1])


# In[44]:


data


# In[45]:


X = data.iloc[:,0:-1]
y = data.iloc[:,-1]
X


# In[46]:


y.unique()


# In[47]:


#find correlation between features
import seaborn as sns
import matplotlib.pyplot as plt
corr = data.corr()
plt.figure(figsize=(20,20))
sns.heatmap(corr, annot=True, cmap=plt.cm.Reds)
plt.show()


# In[48]:


#give the correlation between features and class
corr['Class']


# In[50]:


#split the data into train and test
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X,y, test_size=0.2, random_state=42)


# In[52]:


#train the model using logistic regression
from sklearn.linear_model import LogisticRegression
logreg = LogisticRegression(penalty='none', class_weight=None)
logreg.fit(X_train, y_train)


# In[53]:


#predict the test data
y_pred = logreg.predict(X_test)


# In[56]:


#find the accuracy of the model
from sklearn.metrics import accuracy_score
accuracy_score(y_test, y_pred)


# Class Weight = balanced

# In[57]:


X_train1, X_test1, y_train1, y_test1 = train_test_split(X,y, test_size=0.2, random_state=42)


# In[58]:


logreg1 = LogisticRegression(penalty='none', class_weight='balanced')
logreg1.fit(X_train1, y_train1)


# In[59]:


y_pred1 = logreg1.predict(X_test1)


# In[60]:


accuracy_score(y_test1, y_pred1)


# max_iter=1000

# In[61]:


X_train2, X_test2, y_train2, y_test2 = train_test_split(X,y, test_size=0.2, random_state=42)
logreg2 = LogisticRegression(penalty='none', class_weight=None, max_iter=1000)
logreg2.fit(X_train2, y_train2)


# In[62]:


y_pred2 = logreg2.predict(X_test2)


# In[63]:


accuracy_score(y_test2, y_pred2)


# max_iter=1000, class_weight=balanced

# In[64]:


X_train3, X_test3, y_train3, y_test3 = train_test_split(X,y, test_size=0.2, random_state=42)
logreg3 = LogisticRegression(penalty='none', class_weight='balanced', max_iter=1000)
logreg3.fit(X_train3, y_train3)


# In[65]:


y_pred3 = logreg3.predict(X_test3)


# In[66]:


accuracy_score(y_test3, y_pred3)


# In[67]:


#decision_function
logreg3.decision_function(X_test3)


# In[69]:


y_pred3


# In[70]:


#predict_proba
logreg3.predict_proba(X_test3)


# In[72]:


#balanced_accuracy_score for class_weight=balanced and max_iter=1000
from sklearn.metrics import balanced_accuracy_score
balanced_accuracy_score(y_test3, y_pred3)


# In[73]:


#balanced_accuracy_score for class_weight=None and max_iter=1000
balanced_accuracy_score(y_test2, y_pred2)


# In[74]:


#balanced_accuracy_score for class_weight=balanced and max_iter=100
balanced_accuracy_score(y_test1, y_pred1)


# In[75]:


#balanced_accuracy_score for class_weight=None and max_iter=100
balanced_accuracy_score(y_test, y_pred)


# In[76]:


#confusion_matrix for class_weight=balanced and max_iter=100
from sklearn.metrics import confusion_matrix
confusion_matrix(y_test1, y_pred1)


# In[77]:


#confusion_matrix for class_weight=None and max_iter=100
confusion_matrix(y_test, y_pred)


# In[78]:


#confusion_matrix for class_weight=balanced and max_iter=1000
confusion_matrix(y_test3, y_pred3)


# In[79]:


#confusion_matrix for class_weight=None and max_iter=1000
confusion_matrix(y_test2, y_pred2)


# In[86]:


#precision and recall for class_weight=balanced and max_iter=100
from sklearn.metrics import precision_score, recall_score
precision_score(y_test1, y_pred1, average='micro')


# In[88]:


recall_score(y_test1, y_pred1, average='micro')


# In[82]:


precision_score(y_test1, y_pred1, average='macro')


# In[89]:


recall_score(y_test1, y_pred1, average='macro')


# In[90]:


#precision and recall for class_weight=None and max_iter=100
precision_score(y_test, y_pred, average='micro')


# In[91]:


recall_score(y_test, y_pred, average='micro')


# In[92]:


precision_score(y_test, y_pred, average='macro')


# In[93]:


recall_score(y_test, y_pred, average='macro')


# In[94]:


#penalty = l2 for class_weight=balanced and max_iter=100
X_train4, X_test4, y_train4, y_test4 = train_test_split(X,y, test_size=0.2, random_state=42)
logreg4 = LogisticRegression(penalty='l2', class_weight='balanced')
logreg4.fit(X_train4, y_train4)


# In[95]:


y_pred4 = logreg4.predict(X_test4)


# In[96]:


accuracy_score(y_test4, y_pred4)


# Set the penalty parameter in LogisticRegression to ‘l2’. Give the equation of the cost function used by LogisticRegression and the gradient of this l2-regularised cost and explain what this penalty parameter does.
# 
# The cost function used by LogisticRegression with an l2 penalty parameter is:
# 
# Cost = -log(P(y(hat)|x(hat))) + λ*||w||^2
# 
# Where P(y|x) is the probability of the target variable y given the input x, w is the weight vector, and λ is the regularization parameter.
# 
# The gradient of this l2-regularised cost is:
# 
# ∇w Cost = -∇w log(P(y|x)) + 2λ*w
# 
# The penalty parameter (λ) controls the amount of regularization applied to the model. A larger value of λ will result in a more heavily penalized model, which will reduce overfitting but may also reduce accuracy. A smaller value of λ will result in a less heavily penalized model, which may increase accuracy but may also lead to overfitting.

# In[103]:


#implement 2nd order polynomial features
from sklearn.preprocessing import PolynomialFeatures
poly = PolynomialFeatures(2)
X_poly = poly.fit_transform(X)


# In[104]:


X_train5, X_test5, y_train5, y_test5 = train_test_split(X_poly,y, test_size=0.2, random_state=42)
logreg5 = LogisticRegression(penalty='none', class_weight='balanced', max_iter=1000)
logreg5.fit(X_train5, y_train5)


# In[105]:


y_pred5 = logreg5.predict(X_test5)


# In[106]:


accuracy_score(y_test5, y_pred5)


# In[107]:


#give the dimensions of the 2nd order polynomial features
X_poly.shape


# In[108]:


X.shape


# In[109]:


#2nd degree polynomial with l2 penalty
X_train6, X_test6, y_train6, y_test6 = train_test_split(X_poly,y, test_size=0.2, random_state=42)
logreg6 = LogisticRegression(penalty='l2', class_weight='balanced', max_iter=1000)
logreg6.fit(X_train6, y_train6)


# In[110]:


y_pred6 = logreg6.predict(X_test6)


# In[111]:


accuracy_score(y_test6, y_pred6)


# In[112]:


#precision and recall for 2nd degree polynomial with l2 penalty
precision_score(y_test6, y_pred6, average='micro')


# In[113]:


recall_score(y_test6, y_pred6, average='micro')


# In[114]:


precision_score(y_test6, y_pred6, average='macro')


# In[122]:


recall_score(y_test6, y_pred6, average='macro')


# #precision recall curve
# import matplotlib.pyplot as plt
# from sklearn.metrics import (precision_recall_curve,PrecisionRecallDisplay)
# precision, recall, _ = precision_recall_curve(y_test6, y_pred6)
# disp = PrecisionRecallDisplay(precision=precision, recall=recall)
# disp.plot()
# plt.show()

# In[124]:


#confusion matrix for 2nd degree polynomial with l2 penalty
confusion_matrix(y_test6, y_pred6)


# In[125]:


#confusion matrix for 2nd degree polynomial with no penalty
print(confusion_matrix(y_test5, y_pred5))


# In[ ]:




