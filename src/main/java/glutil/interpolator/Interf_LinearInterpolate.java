/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil.interpolator;

/**
 *
 * @author elect
 */
public interface Interf_LinearInterpolate<T> {

    T linearInterpolate(T a, T b, float alpha);
}
