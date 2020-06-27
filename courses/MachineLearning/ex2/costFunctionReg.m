function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta
for i = 1:m,
  x = X(i, :);                                              % ith column of data
  predictions = sigmoid(x * theta);% predictions of hypothesis on all m examples
  logistic = -y(i) * log(predictions) - (1 - y(i)) * log(1 - predictions);
  J = J + logistic;
end
J = J / m;

% adding regularization terms
regterms = 0;
for j = 2:size(theta), % skip theta_0
  regterms = regterms + theta(j) * theta(j);
end
regterms = regterms * lambda * 0.5 / m;
J = J + regterms;

% gradient for theta_0
firstsum = 0;
for i = 1:m,
  x = X(i, :);                                            % ith column of data
  predictions = sigmoid(x * theta);  % predictions of hypothesis on m examples
  firstsum = firstsum + (predictions - y(i)) * x(1);
end
grad(1) = firstsum / m;

% rest of the gradient
for j = 2:size(theta),
  jthsum = 0;
  for i = 1:m,
    x = X(i, :);                                            % ith column of data
    predictions = sigmoid(x * theta);  % predictions of hypothesis on m examples
    jthsum = jthsum + (predictions - y(i)) * x(j);
  end
  grad(j) = jthsum / m + lambda * theta(j) / m;
end
% =============================================================

end
