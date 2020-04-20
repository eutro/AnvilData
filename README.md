![](logo.png)

This mod simply adds a recipe type for anvils, so anvil recipes can be created using data packs.

Here's an example for a recipe that crafts a coal block using 1 coal in the first slot, and 8 coal in the second.

```
{
  "type": "anvildata:anvil_recipe",

  "first": {
    "item": "minecraft:coal"
  },

  "second": {
    "type": "forge:nbt",
    "item": "minecraft:coal",
    "count": 8
  },

  "cost": 2,

  "result": {
    "item": "minecraft:coal_block"
  }
}
```

### What it all means
```
  "type": "anvildata:anvil_recipe"
```
Necessary to declare the recipe type.

```
  "first": {
    "item": "minecraft:coal"
  }
```
First item, necessary, can be any other ingredient notation as well.

```
  "second": {
    "type": "forge:nbt",
    "item": "minecraft:coal",
    "count": 8
  }
```
Second item, also necessary, and can also be any type of ingredient notation.

```
  "cost": 2
```
Level cost, optional. Defaults to 4.

```
  "result": {
    "item": "minecraft:coal_block"
  }
```
Result of the recipe, necessary. Can include NBT tag and count.

More examples can be found [here](https://github.com/eutropius225/AnvilData/tree/master/src/test/resources/assets/anvildata/recipes "Example Recipes").