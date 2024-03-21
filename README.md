## Description:
I18n extends functionality of Adventure's GlobalTranslator and extends its reach to every occurrence of text components using ProtocolLib. I18n also includes a simple file loader for everyone who can't code. With I18n, you can easily make every part of your project fully translatable, including GUIs, Titles, Items and Entity names.

## Usage:
To load translations from files, simply create a directory called "i18n_repo" in the root folder of your server.
There you can organise your translations as you please up to 16 folders deep.
Translation files must be created for each language and named similar to Minecraft locale files and use the file extension .properties e.g. en_US or de_DE.
Translation files contain the translation key, then an equals symbol, and then the localised message e.g. common.rarity.label=Rarity

## Example:

File structure:
```
i18n_repo
 ┗my
  ┗custom
   ┗structure
    ┣ en_US.properties
    ┗ de_DE.properties
```
en_US.properties:
```
common.rarity.label=Rarity
common.rarity.legendary=Legedary
holyStone.name=Holy Stone
holyStone.lore1=This stone was forged
holyStone.lore2=by the great dwarf
holyStone.lore3=a long time ago.
```


de_DE.properties:
```
common.rarity.label=Seltenheit
common.rarity.legendary=Legendär
holyStone.name=Heiliger Stein
holyStone.lore1=Dieser Stein wurde
holyStone.lore2=von dem großen Zwerg
holyStone.lore3=vor unserer Zeit geschmiedet.
```

Now using the following command:
```
give @p stone{display:{Name:'{"translate":"holyStone.name","color":"dark_purple","italic":false}',Lore:['[{"translate":"common.rarity.label","color":"white","italic":false},{"text":" "},{"translate":"common.rarity.legendary","color":"gold","bold":true}]','{"translate":"holyStone.lore1","color":"gray","italic":true}','{"translate":"holyStone.lore2","color":"gray","italic":true}','{"translate":"holyStone.lore3","color":"gray","italic":true}']}} 1
```
You will receive an item called "Holy Stone" that is automatically translated into "Heiliger Stein" if the player's language client language is German.

## Dependencies:
- ProtocolLib

## Issues:
- Items will not be translated correctly if they are in the inventory of a player who is in creative mode. This is an issue caused by the client and cannot be fixed by the plugin.
- I18n also does not automatically detect if a player changes his language, meaning that things like Item names will only update if items are dropped/moved or an entity is respawned.