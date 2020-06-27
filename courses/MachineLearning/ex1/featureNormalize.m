function [X_norm, mu, sigma] = featureNormalize(X)
%FEATURENORMALIZE Normalizes the features in X 
%   FEATURENORMALIZE(X) returns a normalized version of X where
%   the mean value of each feature is 0 and the standard deviation
%   is 1. This is often a good preprocessing step to do when
%   working with learning algorithms.

% You need to set these values correctly
X_norm = X;
% size(X, 2) is the number of columns in X
mu = zeros(1, size(X, 2));
sigma = zeros(1, size(X, 2));

% ====================== YOUR CODE HERE ==== ==================
% Instructions: First, for each feature dimension, compute the mean
%               of the feature and subtract it from the dataset,
%               storing the mean value in mu. Next, compute the 
%               standard deviation of each feature and divide
%               each feature by its standard deviation, storing
%               the standard deviation in sigma. 
%
%               Note that X is a matrix where each column is a 
%               feature and each row is an example. You need 
%               to perform the normalization separately for 
%               each feature. 
%
% Hint: You might find the 'mean' and 'std' functions useful.
%
for i = 1:size(X, 2),
  column = X(:, i);            % get ith feature dimension (column) of dataset X
  column_mean = mean(column);                         % calculate feature's mean
  mu(i) = column_mean;                       % store feature's mean in mu vector
  X_norm(:, i) = X_norm(:, i) - column_mean;% subtract feature's mean from feature
  
  column = X(:, i);                                     % get the updated column
  deviation = std(column);              % calculate feature's standard deviation
  sigma(i) = deviation;     % store feature's standard deviation in sigma vector
  X_norm(:, i) = X_norm(:, i) ./ deviation; % divide feature by feature's stddev
end
% ============================================================

end
