const round = (value, decimals = 1) => {
  return Math.round(value * 10 ** decimals) / 10 ** decimals;
};

const formatInThousands = value => {
  if (typeof value !== 'number') {
    return undefined;
  }

  if (value < 1000) {
    return value.toLocaleString();
  }

  const inThousands = round(value / 1000);

  return `${inThousands.toLocaleString()}k`;
};

export default formatInThousands;