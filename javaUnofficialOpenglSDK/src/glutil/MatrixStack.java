/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import java.util.ArrayList;

/**
 *
 * @author gbarbieri
 */
public class MatrixStack {

    private ArrayList<Mat4> matrices;

    public MatrixStack() {
        matrices = new ArrayList<>();
        matrices.add(new Mat4(1.0f));
    }

    public MatrixStack translate(Vec4 offset) {
        return translate(offset.x, offset.y, offset.z);
    }

    public MatrixStack translate(Vec3 offset) {
        return translate(offset.x, offset.y, offset.z);
    }

    public MatrixStack translate(float x, float y, float z) {
        top().translate(x, y, z);
        return this;
    }

    public MatrixStack scale(Vec3 scaling) {
        return scale(scaling.x, scaling.y, scaling.z);
    }

    public MatrixStack scale(float x, float y, float z) {
        top().scale(x, y, z);
        return this;
    }

    public MatrixStack rotateX(float angDeg) {
        top().rotateX(Math.toRadians(angDeg));
        return this;
    }

    public MatrixStack rotateY(float angDeg) {
        top().rotateY(Math.toRadians(angDeg));
        return this;
    }

    public MatrixStack rotateZ(float angDeg) {
        top().rotateZ(Math.toRadians(angDeg));
        return this;
    }

    public MatrixStack applyMatrix(Mat4 mat4) {
        top().mul(mat4);
        return this;
    }

    public MatrixStack push() {
        matrices.add(new Mat4(top()));
        return this;
    }

    public MatrixStack pop() {
        matrices.remove(matrices.size() - 1);
        return this;
    }

    public Mat4 top() {
        return matrices.get(matrices.size() - 1);
    }

    public MatrixStack setMatrix(Mat4 mat4) {
        matrices.set(matrices.size() - 1, mat4);
        return this;
    }

    public void perspective(float defFOV, float aspectRatio, float zNear, float zFar) {
        top().mulPerspective(defFOV, aspectRatio, zNear, zFar);
    }
}
