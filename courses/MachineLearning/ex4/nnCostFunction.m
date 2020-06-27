function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, 
% the weight matrices for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1); % m = 5000
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%

% Part 1: ---------------------------------------------------
% Add ones to the X data matrix (X is 5000x400)
X = [ones(m, 1) X]; % now X is 5000x401

% As suggested, using a for-loop over the examples to compute the cost
for i = 1:m % there are m = 5000 examples
  x = X(i, :); % this is 1x401
  Y = y(i, :); % the real digit represented by x (this is 1x1)
  
  % Calculating hidden layer
  hidden = sigmoid(x * Theta1'); % (1x401) * (401x25) = 1x25
  
  % Adding ones to the calculated hidden layer (now hidden is 1x26)
  hidden = [1 hidden];
  
  % Calculating output layer
  output = sigmoid(hidden * Theta2'); % (1x26) * (26x10) = 1x10
  
  % Vectorizing Y, e.g. if Y = 5 then it turns into [0 0 0 0 1 0 0 0 0 0]
  temp = zeros(1, size(output, 2));
  temp(Y) = 1;
  Y = temp; % Y is now 1x10

  % Vectorized sum over 10 outputs (the inner sum from k=1 to k=K=10)
  % this is the unregularized cost
  J = J + (-sum(Y .* log(output)) - sum((1 - Y) .* log(1 - output))) / m;
end

% Part 3.1: ----------------------------------------------------
% Adding regularization terms to the cost:
% Drop first column of Theta1
col1 = size(Theta1, 2);
temp1 = Theta1(:, 2:col1); % now Theta1 is 25x400
first = sum(sum(temp1 .* temp1));

% Drop first column of Theta2
col2 = size(Theta2, 2);
temp2 = Theta2(:, 2:col2); % now Theta2 is 10x25
second = sum(sum(temp2 .* temp2));

% Add the regularization terms to the total cost J
J = J + lambda * 0.5 * (first + second) / m;

% Part 2: -----------------------------------------------------
% As suggested, a for-loop that processes one example at a time
Delta1 = 0;
Delta2 = 0;

for t = 1:m
  % Prep
  x = X(t, :); % t-th training example (this is 1x401)
  Y = y(t, :); % the real digit represented by x (this is 1x1)
  
  % Step 1: feedforward
  a1 = x; % this is 1x401
  
  z2 = a1 * Theta1'; % (1x401) * (401x25) = 1x25
  a2 = sigmoid(z2); % 1x25
  a2 = [1 a2]; % adding bias; now a2 is 1x26
  
  z3 = a2 * Theta2'; % (1x26) * (26x10) = 1x10
  a3 = sigmoid(z3); % 1x10
  
  % Step 2: calculate delta3
  temp = zeros(1, size(a3, 2)); % 1x10
  temp(Y) = 1;
  Y = temp; % Y is now 1x10
  delta3 = a3 - Y; % 1x10
  
  % Step 3: calculate delta2
  % (delta3 * Theta2) is (1x10) * (10x26) = 1x26
  z2 = [1 z2]; % now z2 is 1x26
  delta2 = (delta3 * Theta2) .* sigmoidGradient(z2); % delta2 is 1x26
  
  % Step 4: accumulate gradient
  Delta2 = Delta2 + delta3' * a2; % (10x1) * (1x26) = 10x26
  delta2 = delta2(2:end); % now delta2 is 1x25
  Delta1 = Delta1 + delta2' * a1; % (25x1) * (1x401) = 25x401
end

% Step 5: unregularized gradient
Theta1_grad = Delta1 / m; % 25x401, the original size of Theta1
Theta2_grad = Delta2 / m; % 10x26, the original size of Theta2
% -------------------------------------------------------------

% Part 3.2: Regularize gradients
Theta1_grad(:, 2:end) = Theta1_grad(:, 2:end) + (lambda/m) * Theta1(:, 2:end);
Theta2_grad(:, 2:end) = Theta2_grad(:, 2:end) + (lambda/m) * Theta2(:, 2:end);

% =========================================================================

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];


end
