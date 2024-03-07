#!/usr/bin/env python3

import numpy as np
import pandas as pd

from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score
from itertools import accumulate

import seaborn as sns
from matplotlib import pyplot as plt

import time

# df = pd.read_csv('out2.csv')
# uid, pid = 98085, 196

# dfdf = df[(df['user_id'] == uid) & (df['product_id'] == pid)] 
# dfdf.to_csv('filtered.csv')
dfdf = pd.read_csv('filtered.csv')

start = time.perf_counter()

by_days = []

for (_, a) in dfdf.iterrows():
    current_day = int(a.days_since_prior_order)
    by_days += [0] * (current_day - len(by_days) + 1)
    by_days[current_day] = 1

y = list(accumulate(by_days))
X = list(range(len(by_days)))

amn = 200

X_train = np.array(X[:amn]).reshape(-1, 1)
y_train = y[:amn]
X_test = np.array(X[amn:]).reshape(-1, 1)
y_test = y[amn:]

model = LinearRegression()
model.fit(X_train, y_train)

y_test_pred = model.predict(X_test)
y_train_pred = model.predict(X_train)

end = time.perf_counter()
print(end - start, 'sec for one')
print((end - start) * 500, 'sec for all')


# n_show = 16

# sns.scatterplot(y_test[:n_show], label='true', alpha=0.8, color='orange')
# sns.lineplot(y_test_pred[:n_show], label='pred', alpha=0.5, color='red')
# # sns.lineplot([0.5] * n_show, alpha=0.5, color='green')
# plt.show()

# print(r2_score(y_test[:n_show], y_test_pred[:n_show]))
