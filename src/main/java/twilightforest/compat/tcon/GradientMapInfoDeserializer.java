package twilightforest.compat.tcon;

import com.google.gson.annotations.SerializedName;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;

public class GradientMapInfoDeserializer extends AbstractRenderInfoDeserializer {
    @SuppressWarnings("WeakerAccess")
    @SerializedName("gradient_map")
    protected SerializedGradientNode[] serializedGradientMap;

    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        GradientNode[] gradientMap = new GradientNode[serializedGradientMap.length];

        for (int iteration = 0; iteration < serializedGradientMap.length - 1; iteration++) {
            int minimumIndex = iteration;

            for (int search = iteration + 1; search < serializedGradientMap.length; search++)
                if (serializedGradientMap[search].node < serializedGradientMap[minimumIndex].node)
                    minimumIndex = search;

            SerializedGradientNode accumulator = serializedGradientMap[minimumIndex];
            serializedGradientMap[minimumIndex] = serializedGradientMap[iteration];
            serializedGradientMap[iteration] = accumulator;
        }

        for (int i = 0; i < serializedGradientMap.length; i++) {
            gradientMap[i] = new GradientNode();
            gradientMap[i].node = serializedGradientMap[i].node;
            gradientMap[i].color = fromHex(serializedGradientMap[i].color);
        }

        return new GradientMapInfo(gradientMap);
    }

    @SuppressWarnings("unused")
    class GradientNode {
        float node;
        int color;
    }

    @SuppressWarnings("unused")
    class SerializedGradientNode {
        float node;
        String color;
    }
}
