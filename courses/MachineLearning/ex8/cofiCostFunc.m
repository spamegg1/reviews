function [J, grad] = cofiCostFunc(params, Y, R, num_users, num_movies, ...
                                  num_features, lambda)
%COFICOSTFUNC Collaborative filtering cost function
%   [J, grad] = COFICOSTFUNC(params, Y, R, num_users, num_movies, ...
%   num_features, lambda) returns the cost and gradient for the
%   collaborative filtering problem.
%

% Unfold the U and W matrices from params
X = reshape(params(1:num_movies*num_features), num_movies, num_features);
Theta = reshape(params(num_movies*num_features+1:end), ...
                num_users, num_features);

            
% You need to return the following values correctly
J = 0;
X_grad = zeros(size(X));
Theta_grad = zeros(size(Theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost function and gradient for collaborative
%               filtering. Concretely, you should first implement the cost
%               function (without regularization) and make sure it is
%               matches our costs. After that, you should implement the 
%               gradient and use the checkCostFunction routine to check
%               that the gradient is correct. Finally, you should implement
%               regularization.
%
% Notes: X - num_movies  x num_features matrix of movie features
%        Theta - num_users  x num_features matrix of user features
%        Y - num_movies x num_users matrix of user ratings of movies
%        R - num_movies x num_users matrix, where R(i, j) = 1 if the 
%            i-th movie was rated by the j-th user
%
% You should set the following variables correctly:
%
%        X_grad - num_movies x num_features matrix, containing the 
%                 partial derivatives w.r.t. to each element of X
%        Theta_grad - num_users x num_features matrix, containing the 
%                     partial derivatives w.r.t. to each element of Theta
%

% Computing collaborative filtering cost
% X is num_movies x num_features, Theta' is num_features x num_users
% X * Theta' is num_movies x num_users, so are Y and R
% the element-wise multiplication with R takes care of "if R(i, j) == 1"
J = sum(sum((X * Theta' - Y).^2 .* R)) / 2;

% Computing collaborative filtering gradient
% X is num_movies x num_features, Theta' is num_features x num_users
% X * Theta' is num_movies x num_users, so are Y and R
% the element-wise multiplication with R takes care of "if R(i, j) == 1"
% Theta is num_users x num_features, so X_grad is num_movies x num_features
X_grad = ((X * Theta' - Y).* R) * Theta;

% X is num_movies x num_features, Theta' is num_features x num_users
% X * Theta' is num_movies x num_users, so are Y and R
% the element-wise multiplication with R takes care of "if R(i, j) == 1"
% (X * Theta' - Y).* R)' is num_users x num_movies
% X is num_movies x num_features, so Theta_grad is num_users x num_features
Theta_grad = ((X * Theta' - Y).* R)' * X;

% Computing regularized cost
J = J + lambda * (sum(sum(Theta.^2)) + sum(sum(X.^2))) / 2;

% Computing regularized gradients
X_grad = X_grad + lambda * X;
Theta_grad = Theta_grad + lambda * Theta;

% =============================================================

grad = [X_grad(:); Theta_grad(:)];

end














