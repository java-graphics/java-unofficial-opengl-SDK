/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil.interpolator;

import glm.vec._4.Vec4;

/**
 * @author elect
 */
public class Vec4_ implements Interf_LinearInterpolate<Vec4_> {

	Vec4 value = new Vec4();

	//    public Vector3IDistance(float x, float y, float z) {
	//        vec4 = new Vector3f(x, y, z);
	//    }
	//
	public Vec4_(Vec4 v) {
		value.set(v);
	}
	//
	//    public Vector3f GetValue() {
	//        return vec4;
	//    }
	//
	//    public float distance(Vector3IDistance a, Vector3IDistance b) {
	//        return Vector3f.distance(a.GetValue(), b.GetValue());
	//    }

	@Override
	public Vec4_ linearInterpolate(Vec4_ a, Vec4_ b, float sectionAlpha) {
		Vec4 interpolatedValue = a.value.mul_(new Vec4(1f - sectionAlpha)).add(b.value.mul_(new Vec4(sectionAlpha)));
		return new Vec4_(interpolatedValue);
	}
}
