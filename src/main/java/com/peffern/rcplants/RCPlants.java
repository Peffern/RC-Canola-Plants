package com.peffern.rcplants;


import com.bioxx.tfc.TerraFirmaCraft;
import com.peffern.crops.BaseCrop;
import com.peffern.crops.CropsRegistry;
import com.peffern.crops.RenderCustomCrop;

import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;




/**
 * Mod to make RotaryCraft Canola a TFC crop
 * @author peffern
 *
 */
@Mod(modid = RCPlants.MODID, name = RCPlants.MODNAME, version = RCPlants.VERSION, dependencies = "required-after:terrafirmacraft;required-after:RotaryCraft")
public class RCPlants 
{
	/** We need our own hemp seed item since tfccrops will generate one */
	static Item canolaSeeds;
	
	/** Mod ID String */
	public static final String MODID = "rcplants";
	
	/** Mod Name */
	public static final String MODNAME = "RC Plants";
	
	/** Mod Version */
	public static final String VERSION = "1.1";
	
	/**
	 * Do all the main mod setup
	 */
	@EventHandler
	public void init(FMLInitializationEvent e)
	{		
		
		String[] iconNames = new String[10];
		for(int i = 0; i < iconNames.length; i++)
		{
			iconNames[i] = "RotaryCraft" + ":" + "canola/" + i;
		}
		
		BaseCrop p = new BaseCrop("canola", 1, 28, iconNames, 10, 5, 1.0f, null, "seedsCanola", com.bioxx.tfc.Reference.MOD_ID + ":" + "food/unused/img135", "Seeds Canola")
		{
			@Override
			public ItemStack getOutput1()
			{
				//add 1 to 13 additional canola seeds to the drop 
				int stack = 1;
				if(TerraFirmaCraft.proxy.getCurrentWorld() != null)
					stack += TerraFirmaCraft.proxy.getCurrentWorld().rand.nextInt(13);
				return new ItemStack(canolaSeeds, stack);
			}
			
			@Override
			public boolean render(Block block, double x, double y, double z, RenderBlocks renderblocks) 
			{
				//use the block crops render impl
				RenderCustomCrop.renderBlockCropsImpl(block,x,y,z,renderblocks,0.45,1.0);
				return true;
			}
		};
		canolaSeeds = CropsRegistry.addCrop(p);
		
		
		//do recipes
		ItemStack in = new ItemStack(canolaSeeds);
		
		ItemStack out = ItemStacks.canolaHusks;
		
		TileEntityGrinder.addGrindableSeed(in, 1F);
		
		RecipesGrinder.getRecipes().addCustomRecipe(in, out);
		
	}
}
