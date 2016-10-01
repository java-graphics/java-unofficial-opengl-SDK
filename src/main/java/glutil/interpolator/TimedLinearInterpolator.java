/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil.interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elect
 * @param <T>
 */
public class TimedLinearInterpolator<T extends Interf_LinearInterpolate<T> & Interf_Distance<T>> 
        extends WeightedLinearInterpolator<T> {

    public void SetValues(List<? extends Interf_GetValueTime<T>> dataSet) {
        values = new ArrayList<Data>();

        for (int i = 0; i < dataSet.size(); i++) {
            Data currData = new Data();
            currData.data = dataSet.get(i).GetValue();
            currData.weight = dataSet.get(i).GetTime();
            values.add(currData);
        }

        //Compute the distances of each segment.
        float totalDist = 0.0f;
        for (int iLoop = 1; iLoop < values.size(); ++iLoop) {
            totalDist += Distance(iLoop - 1, iLoop);
            values.get(iLoop).weight = totalDist;
        }

        //Compute the alpha value that represents when to use this segment.
        for (int i = 1; i < values.size(); ++i) {
            values.get(i).weight /= totalDist;
        }
    }
}
