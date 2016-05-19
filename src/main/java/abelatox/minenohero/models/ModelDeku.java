package abelatox.minenohero.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelDeku extends ModelBase
{
	public ModelRenderer head = new ModelRenderer (this,0,0);
	public ModelRenderer body;
	public ModelRenderer arm1;
	public ModelRenderer arm2;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	
	public modelDeku()
	{
		textureHeight = 64;
		textureWidth = 64;
		
		this.head = new ModelRenderer(this,1,1);
		//TODO model this.head.addBox(offX, offY, offZ, width, height, depth)
	}
	
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		
		this.head.render(scale);
		this.body.render(scale);
		this.arm1.render(scale);
		this.arm2.render(scale);
		this.leg1.render(scale);
		this.leg2.render(scale);
				
	}
	
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	
    }

}
	
