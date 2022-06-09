# InvestIN - Machine Learning
Revenue Growth Regression with Deep Learning using [TensorFlow Keras](https://www.tensorflow.org/api_docs/python/tf/keras)

## Dataset
- [2018_Financial_Data.csv](https://www.kaggle.com/datasets/cnic92/200-financial-indicators-of-us-stocks-20142018?select=2018_Financial_Data.csv)

## Data Preprocessing & EDA
- Check and Handle Missing Values using [KNNImputer](https://scikit-learn.org/stable/modules/generated/sklearn.impute.KNNImputer.html)
- Feature Selection using Permutation Importance with [ELI5 Permutation Importance](https://eli5.readthedocs.io/en/latest/blackbox/permutation_importance.html)
- Clean Data from Outlier using Interquartile Range (IQR)

## Data Modeling
Build and train [TensorFlow Sequential Model](https://www.tensorflow.org/api_docs/python/tf/keras/Sequential) with this summary
<p><img src="https://github.com/ayaazzara/InvestIN/blob/ML/model%20summary.png" width="400"><p>

## Model Evaluation
- R2 Score: 0.764
- Model Prediction vs True Value
<p><img src="https://github.com/ayaazzara/InvestIN/blob/ML/true%20values%20vs%20prediction.png" width="400"><p>

## Model Deployment
The trained is model converted to TFLite format and then deployed to Firebase
