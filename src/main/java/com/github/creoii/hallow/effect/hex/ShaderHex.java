package com.github.creoii.hallow.effect.hex;

import com.github.creoii.creolib.api.util.misc.ClientUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import java.util.List;

public class ShaderHex extends Hex {
    public static final List<Identifier> POST_PROCESSORS = new ImmutableList.Builder<Identifier>()
            .add(new Identifier("shaders/post/antialias.json"))
            .add(new Identifier("shaders/post/art.json"))
            .add(new Identifier("shaders/post/bits.json"))
            .add(new Identifier("shaders/post/blobs.json"))
            .add(new Identifier("shaders/post/blobs2.json"))
            .add(new Identifier("shaders/post/blur.json"))
            .add(new Identifier("shaders/post/bumpy.json"))
            .add(new Identifier("shaders/post/color_convolve.json"))
            .add(new Identifier("shaders/post/creeper.json"))
            .add(new Identifier("shaders/post/deconverge.json"))
            .add(new Identifier("shaders/post/desaturate.json"))
            .add(new Identifier("shaders/post/entity_outline.json"))
            .add(new Identifier("shaders/post/flip.json"))
            .add(new Identifier("shaders/post/fxaa.json"))
            .add(new Identifier("shaders/post/green.json"))
            .add(new Identifier("shaders/post/invert.json"))
            .add(new Identifier("shaders/post/notch.json"))
            .add(new Identifier("shaders/post/ntsc.json"))
            .add(new Identifier("shaders/post/outline.json"))
            .add(new Identifier("shaders/post/pencil.json"))
            .add(new Identifier("shaders/post/phosphor.json"))
            .add(new Identifier("shaders/post/scan_pincushion.json"))
            .add(new Identifier("shaders/post/sobel.json"))
            .add(new Identifier("shaders/post/spider.json"))
            .add(new Identifier("shaders/post/transparency.json"))
            .add(new Identifier("shaders/post/wobble.json"))
            .build();

    public ShaderHex() {
        super(StatusEffectCategory.HARMFUL, 1, true);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient) {
            Identifier id = POST_PROCESSORS.get(entity.getRandom().nextInt(POST_PROCESSORS.size()));
            System.out.println(id);
            ClientUtil.loadPostProcessor(id);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient)
            ClientUtil.clearPostProcessing();
    }
}
