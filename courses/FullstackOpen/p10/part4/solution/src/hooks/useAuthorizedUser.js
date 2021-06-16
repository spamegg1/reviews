import { useQuery } from '@apollo/react-hooks';

import { GET_AUTHORIZED_USER } from '../graphql/queries';

const useAuthorizedUser = (variables) => {
  const { data, ...rest } = useQuery(GET_AUTHORIZED_USER, {
    variables,
    fetchPolicy: 'cache-and-network',
  });

  const authorizedUser = data ? data.authorizedUser : undefined;

  return { authorizedUser, ...rest };
};

export default useAuthorizedUser;
