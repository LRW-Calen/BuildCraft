package buildcraft.lib.item;

import buildcraft.lib.BCLib;
import buildcraft.lib.misc.NBTUtilBC;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ItemGuideNote extends ItemBC_Neptune
{

    public ItemGuideNote(String idBC, Item.Properties properties)
    {
        super(idBC, properties);
    }

    public static String getNoteId(ItemStack stack)
    {
        return NBTUtilBC.getItemData(stack).getString("note_id");
    }

    public ItemStack storeNoteId(String noteId)
    {
        ItemStack stack = new ItemStack(this);
        NBTUtilBC.getItemData(stack).putString("note_id", noteId);
        return stack;
    }

    @Override
//    public ActionResult<ItemStack> onItemRightClick(Level world, Player player, InteractionHand hand)
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        // TODO Calen GuideNote gui not impl in 1.12.2
//        player.openGui(BCLib.INSTANCE, 1, world, 0, 0, 0);
        if (player instanceof ServerPlayer serverPlayer)
        {
//            NetworkHooks.openGui(serverPlayer, this);
        }
//        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }
}
