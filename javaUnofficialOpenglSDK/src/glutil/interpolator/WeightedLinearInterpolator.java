/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil.interpolator;

import java.util.ArrayList;
import java.util.List;
import one.util.streamex.StreamEx;

/**
 *
 * @author elect
 * @param <T>
 */
public class WeightedLinearInterpolator<T extends Interf_LinearInterpolate<T> & Interf_Distance<T>> {

    class Data {

        public T data;
        public float weight;
    }

    protected List<Data> values = new ArrayList<>();

    public T interpolate(float alpha) {

        if (values.isEmpty()) {
            return null;
        } else if (values.size() == 1) {
            return values.get(0).data;
        }

        //Find which segment we are within.
        int segment = (int) StreamEx.of(values).takeWhile(v -> v.weight >= alpha).count();
//        int segment = 1;
//        for (; segment < values.size(); ++segment) {
//            if (alpha < values.get(segment).weight) {
//                break;
//            }
//        }

        if (segment == values.size()) {
            return values.get(values.size() - 1).data;
        }

        float sectionAlpha = alpha - values.get(segment - 1).weight;
        sectionAlpha /= values.get(segment).weight - values.get(segment - 1).weight;

        float invSecAlpha = 1.0f - sectionAlpha;

        return LinearInterpolate(segment - 1, segment, sectionAlpha);
    }

    protected T LinearInterpolate(int a, int b, float sectionAlpha) {
        return values.get(a).data.linearInterpolate(values.get(a).data, values.get(b).data, sectionAlpha);
    }

    protected float Distance(int a, int b) {
        return values.get(a).data.distance(values.get(a).data, values.get(b).data);
    }
}
