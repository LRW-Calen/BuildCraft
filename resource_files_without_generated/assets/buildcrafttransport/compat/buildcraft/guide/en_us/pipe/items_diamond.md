<lore>
Choosing which items can travel where might be useful for your system.
</lore>
<no_lore>
A Diamond Transport Pipe is used to transport items from a pipe in a direction set by its filter into either another adjacent pipe or inventory.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_diamond_colorless"/>

<chapter name="Setting the Filter"/>
The Diamond Transport Pipe has each of its sides distinguished by a different colour.
Opening up the Diamond Transport Pipe GUI will allow you to setup a filter.
Placing a items you want in a certain slots colour will allow you to filter which items can go in which direction.
Having multiple of the same items in a colour will allow you to add 'weight' to where you fluids will go.

A List can be used in a filtering slots to give you expanded filtering options.
<link to="buildcraftcore:item/list"/> 

<chapter name="Pipe Mechanics"/>
In case of their being multiple directions items could go, the items will be distributed (depending of the weight you give them) between them.
Any valid direction with no filter set will allow any item to pass through but filtered directions will take priority.
Items will not travel back in the direction it came from and only in a different direction.
If there is no connecting pipe or the adjacent inventory is full and there is no where else for the item to go, the item will drop out of the pipe onto the ground.

Some machines can automatically output items directly into a Diamond Transport Pipe without needing to be fed through an extraction pipe.
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<usages stack="buildcrafttransport:pipe_items_diamond_colorless"/>