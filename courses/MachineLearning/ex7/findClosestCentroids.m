function idx = findClosestCentroids(X, centroids)
%FINDCLOSESTCENTROIDS computes the centroid memberships for every example
%   idx = FINDCLOSESTCENTROIDS (X, centroids) returns the closest centroids
%   in idx for a dataset X where each row is a single example. idx = m x 1 
%   vector of centroid assignments (i.e. each entry in range [1..K])
%

% Set K
K = size(centroids, 1);

% You need to return the following variables correctly.
idx = zeros(size(X,1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Go over every example, find its closest centroid, and store
%               the index inside idx at the appropriate location.
%               Concretely, idx(i) should contain the index of the centroid
%               closest to example i. Hence, it should be a value in the 
%               range 1..K
%
% Note: You can use a for-loop over the examples to compute this.
%
for i = 1:size(X,1) % go over every example
  example = X(i, :); % ith example (row) from the X matrix
  closest = 0; % index of centroid closest to example so far
  dist = inf; % shortest distance from a centroid to example so far
  
  for j = 1:K % go over every centroid
    centroid = centroids(j, :); % jth centroid (row) from centroids
    newdist = norm(example - centroid);
    
    if newdist < dist % found a closer centroid than before
      dist = newdist; % update shortest distance so far
      closest = j; % update closest centroid so far
    end
  end
  
  idx(i) = closest;
end

% =============================================================

end

