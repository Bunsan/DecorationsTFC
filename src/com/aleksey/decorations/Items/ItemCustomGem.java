package com.aleksey.decorations.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.aleksey.decorations.Core.BlockList;
import com.aleksey.decorations.TileEntities.TileEntityGem;
import com.bioxx.tfc.Items.ItemGem;


public class ItemCustomGem extends ItemGem
{
    public ItemCustomGem()
    {
        super();
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlock(x, y, z);
        
        if (!block.isSideSolid(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[side]))
            return false;
        
        int gemX = x;
        int gemY = y;
        int gemZ = z;
        
        switch(side)
        {
            case 0:
                gemY--;
                break;
            case 1:
                gemY++;
                break;
            case 2:
                gemZ--;
                break;
            case 3:
                gemZ++;
                break;
            case 4:
                gemX--;
                break;
            case 5:
                gemX++;
                break;
        }
        
        if(!world.isAirBlock(gemX, gemY, gemZ))
            return false;
        
        world.setBlock(gemX, gemY, gemZ, BlockList.Gem, side, 0x2);
        
        if(world.isRemote)
            world.markBlockForUpdate(gemX, gemY, gemZ);

        TileEntityGem tileEntity = (TileEntityGem)world.getTileEntity(gemX, gemY, gemZ);
        
        if(tileEntity != null)
            tileEntity.setInventorySlotContents(0, new ItemStack(itemstack.getItem(), 1, itemstack.getItemDamage()));
        
        ItemStack newItemStack = itemstack.stackSize == 1
                ? null
                : new ItemStack(itemstack.getItem(), itemstack.stackSize - 1, itemstack.getItemDamage());
        
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, newItemStack);        
        entityplayer.onUpdate();

        return true;
    }
}