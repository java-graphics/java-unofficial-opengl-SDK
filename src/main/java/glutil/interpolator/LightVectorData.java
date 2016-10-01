/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil.interpolator;

import glm.vec._4.Vec4;

/**
 *
 * @author elect
 */
public class LightVectorData implements Interf_LinearInterpolate<LightVectorData>, Interf_Distance<LightVectorData>, Interf_GetValueTime<LightVectorData>{

    private Vec4 v;
    private float f;

    public LightVectorData(Vec4 v, float f) {
        this.v = v;
        this.f = f;
    }
    
    @Override
    public LightVectorData linearInterpolate(LightVectorData a, LightVectorData b, float alpha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float distance(LightVectorData a, LightVectorData b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LightVectorData GetValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float GetTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
