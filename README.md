# Shopping Cart Service #

The shopping cart service manages information about items put into a customers cart

## Building
```./gradlew build```

## API ##

| METHOD | PATH | Accept | Body | DESCRIPTION |
| ------ |----- | ------ |----- | ----------- |
| **GET**    | {userId}/items |        |      | List all items in user's cart |
| **POST**    | {userId}/add | application/json |   { uuid: '12345', quantity: 1 }   | Add item with given quanity to cart |
| **POST**    | {userId}/remove | application/json |   { uuid: '12345' }   | Remove item from cart |
