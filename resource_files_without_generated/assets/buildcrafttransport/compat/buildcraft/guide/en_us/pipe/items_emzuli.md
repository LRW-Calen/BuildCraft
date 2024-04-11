<lore>
Using a gate to extract certain items is the latest technical revolution in filtering technology.
</lore>
<no_lore>
A Emzuli Transport Pipe is gate controlled pipe used to extract selected items determined by its filter from an inventory when given power.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_emzuli_colorless"/>

<chapter name="Pipe Mechanics"/>
When powered, It will extract the items selected in the filter and transport them to any other connected inventories/pipes.
The extraction of items will occur out of solid side of the pipe and transport them towards the clear facing sides.
If it has multiple clear facing sides, the items move in a random direction.
If there is no where for the item to go, the extracted items will drop out of the pipe onto the ground.

Some machines can automatically output items directly into a Emzuli Pipe without the pipe needing to be powered (This will not respect the filters).
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Filtering"/>
Opening up the Emzuli Transport Pipe GUI will four colours for four separate filtering options.
Placing items in the slots will allow you to what each filter extracts.
The colours of the paintbrushes next to the slots will determine what colour they will be painted when extracted can can be changed by clicking on them.
To select which of the four filters is extracted, It requires a gate placed on it with a coloured Extraction Preset action provided. I.e. Redstone signal - > Red Extraction Preset.

A List can be used in a filtering slots to give you expanded filtering options.
<link to="buildcraftcore:item/list"/> 

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of power it receives, it will extract items from the adjacent inventory.
The more power a pulse has, the more items will be extracted per pulse.

<usages stack="buildcrafttransport:pipe_items_emzuli_colorless"/>