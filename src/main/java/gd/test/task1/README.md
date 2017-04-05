Given a list of products, represented in json as follows

`[{productId: '1234', type: 'Shirt', category: 3, price: 40},
{productId: '2341', type: 'Pants', category: 3a, price: 15},
{productId: '123', type: 'Shoe', category: 1, price: 20},
{productId: '234', type: 'Socks', category: 1a, price: 5}]`

Create a sorted list of product pairings by matching similar categories (example category 1 and 1a, 2 and 2a etc.
Output list should be presented in sorted sequence of total cost of pair.

Expected output:

`[{{productId: '123', type: 'Shoe', category: 1, price: 20},
{productId: '234', type: 'Socks', category: 1a, price: 5}},
{{productId: '1234', type: 'Shirt', category: 3, price: 40},
{productId: '2341', type: 'Pants', category: 3a, price: 15}}]`