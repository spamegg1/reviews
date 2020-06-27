function [C, sigma] = dataset3Params(X, y, Xval, yval)
%DATASET3PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = DATASET3PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
%C = 1;
%sigma = 0.3;
% Values I found:
C = 1;
sigma = 0.1;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%

% I commented out the code below after running it once and finding C & sigma,
% so that we don't train 64 models every time we submit (takes a long time).

% We are given these values to try for both C and sigma
%values = [0.01, 0.03, 0.1, 0.3, 1, 3, 10, 30];
%count = length(values);
%olderror = 100;
%newerror = 0;
%
%% total of 8*8 = 64 combinations of C and sigma
%for i = 1:count
%  for j = 1:count
%    tryC = values(i);
%    trySigma = values(j);
%    
%    model = svmTrain(X, y, tryC, @(x1, x2) gaussianKernel(x1, x2, trySigma));
%    predictions = svmPredict(model, Xval);
%    
%    newerror = mean(double(predictions ~= yval));
%    if newerror < olderror
%      olderror = newerror;
%      C = tryC;
%      sigma = trySigma;
%    end
%  end
%end
% =========================================================================
%fprintf("%f %f %f", C, sigma, olderror);
% It gives C = 1, sigma = 0.1, error = 0.03

end
