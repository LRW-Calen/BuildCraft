<lore>
Want to take items out and transport them somewhere else? Just give it power and watch it them flow.
</lore>
<no_lore>
A Wooden Transport Pipe is used to extract items from an inventory when given power.
</no_lore>

<recipes stack="buildcrafttransport:pipe_items_wood_colorless"/>

<chapter name="Pipe Mechanics"/>
When powered, It will extract and transport the items to any other connecting inventories/pipes.
The extraction of items will occur out of solid side of the pipe and transport them towards the clear facing sides.
If it has multiple clear facing sides, the items will move randomly between them.
If there is no where for the items to go, the extracted items will drop out of the pipe onto the ground.

Some machines can automatically output items directly into a Wooden Transport Pipe without the pipe needing to be powered.
Using Pipe Plugs or painting pipes a different colour can stop pipes connecting.

<chapter name="Powering"/>
It can receive power from any MJ power source such as: an engine, a power adaptor, or a pipe pulsar.
<link to="buildcraftcore:block/engine_wood"/>
<link to="buildcrafttransport:item/plug_power_adaptor"/>
<link to="buildcraftsilicon:item/plug_pulsar"/> 
For each pulse of power it receives, it will extract items from the adjacent inventory.
The more power a pulse has, the more items will be extracted per pulse.

<usages stack="buildcrafttransport:pipe_items_wood_colorless"/>