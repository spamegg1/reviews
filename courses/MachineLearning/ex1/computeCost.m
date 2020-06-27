function J = computeCost(X, y, theta)
%COMPUTECOST Compute cost for linear regression
%   J = COMPUTECOST(X, y, theta) computes the cost of using theta as the
%   parameter for linear regression to fit the data points in X and y

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly
J = 0;

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta
%               You should set J to the cost.
for i = 1:m,
  x = X(i, :);                                              % ith column of data
  predictions = x * theta;         % predictions of hypothesis on all m examples
  sqrErrors = (predictions - y(i)).^2;                          % squared errors
  J = J + sqrErrors;
end;

J = J / (2*m);

% =========================================================================

end
