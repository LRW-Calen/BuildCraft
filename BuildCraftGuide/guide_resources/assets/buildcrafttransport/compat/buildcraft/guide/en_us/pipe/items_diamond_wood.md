<lore>
Want to take certain items out and transport them somewhere else? Just give it power and watch them flow.
</lore>
<no_lore>
A Wooden Diamond Transport Pipe is used to extract selected items determined by its filter from an inventory when given power.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_diamond_wood_colorless"/>

<chapter name="Pipe Mechanics"/>
When powered, It will extract the items selected in the filter and transport them to any other connected inventories/pipes.
The extraction of items will occur out of solid side of the pipe and transport them towards the clear facing sides.
If it has multiple clear facing sides, the items will move in a random direction.
If there is no where for the item to go, the extracted items will drop out of the pipe onto the ground.

Some machines can automatically output items directly into a Wooden Diamond Transport Pipe without the pipe needing to be powered (This will not respect the filter).
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Filtering"/>
Opening up the Wooden Diamond Transport Pipe GUI will allow you to setup a filter.
Placing items you want in the slots will allow you to filter what it extracts.
You can use the Whitelist, Blacklist or Round Robin options to change what your filter does:
Whitelist: Only the items selected will be extracted.
Blacklist: Any items other than the items selected will be extracted.
Round robin: Extracts the items in the exact order selected in the above filter slots (from left to right)

A List can be used in a filtering slots to give you expanded filtering options.
<link to="buildcraftcore:item/list"/> 

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of power it receives, it will extract items from the adjacent inventory.
The more power a pulse has, the more items will be extracted per pulse.

<usages stack="buildcrafttransport:pipe_items_diamond_wood_colorless"/>