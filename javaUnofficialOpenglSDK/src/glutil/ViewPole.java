/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil;

import com.jogamp.newt.event.MouseEvent;
import static glutil.ViewPole.Projection.perspective;
import jglm.Jglm;
import jglm.Mat4;
import jglm.Quat;
import jglm.Vec2;
import jglm.Vec3;

/**
 *
 * @author gbarbieri
 */
public class ViewPole {

    private ViewData currView;
    private ViewData initialView;
    private ViewScale viewScale;
    private boolean isDragging;
    private RotatingMode rotatingMode;
    private Vec2 startDragMouseLoc;
    private float degStartDragSpin;
    private Quat startDragOrient;
    private float scale;
    private Projection type;

    public ViewPole(ViewData viewData, ViewScale viewScale, Projection type) {

        this.currView = viewData;
        this.initialView = viewData;
        this.viewScale = viewScale;
        this.type = type;

        isDragging = false;
        scale = 1.0f;
    }

    public Mat4 calcMatrix() {

        Mat4 mat = new Mat4(1.0f);

        mat = Jglm.translate(mat, new Vec3(0.0f, 0.0f, -currView.getRadius()));
//        System.out.println("currView.getRadius(): "+(-currView.getRadius()));
//        mat.print("mat");

        Quat fullRotation = Jglm.angleAxis(currView.getDegSpinRotation(), new Vec3(0.0f, 0.0f, 1.0f));
        fullRotation = fullRotation.mult(currView.getOrient());

        mat = mat.mult(fullRotation.toMatrix());

        mat = Jglm.translate(mat, currView.getTargetPos().negated());

        return mat;
    }

    public void mousePressed(MouseEvent mouseEvent) {

        if (!isDragging) {

            if (mouseEvent.isButtonDown(MouseEvent.BUTTON1)) {

                Vec2 position = new Vec2(mouseEvent.getX(), mouseEvent.getY());

                if (mouseEvent.isControlDown()) {

                    beginDragRotate(position, RotatingMode.BIAXIAL);

                } else if (mouseEvent.isAltDown()) {

                    beginDragRotate(position, RotatingMode.SPIN);

                } else {

                    beginDragRotate(position, RotatingMode.DUAL_AXIS);
                }
            }
        }
    }

    private void beginDragRotate(Vec2 position, RotatingMode rotatingMode) {

        this.rotatingMode = rotatingMode;

        startDragMouseLoc = position;

        degStartDragSpin = currView.getDegSpinRotation();

        startDragOrient = currView.getOrient();

        isDragging = true;
    }

    public void mouseMoved(MouseEvent mouseEvent) {

        if (isDragging) {

            onDragRotate(mouseEvent);
        }
    }

    private void onDragRotate(MouseEvent mouseEvent) {

        Vec2 current = new Vec2(mouseEvent.getX(), mouseEvent.getY());

        current = current.minus(startDragMouseLoc);

        switch (rotatingMode) {

            case DUAL_AXIS:
                processXYchange(current);
                break;

            case SPIN:
                processSpinAxis(current);
                break;
        }
    }

    private void processXYchange(Vec2 diff) {

        diff = diff.times(viewScale.getRotationScale());

        Quat yWorldSpace = Jglm.angleAxis(diff.x, new Vec3(0.0f, 1.0f, 0.0f));

        currView.setOrient(startDragOrient.mult(yWorldSpace));

        Quat xLocalSpace = Jglm.angleAxis(diff.y, new Vec3(1.0f, 0.0f, 0.0f));

        currView.setOrient(xLocalSpace.mult(currView.getOrient()));
    }

    private void processSpinAxis(Vec2 diff) {

        float degSpinDiff = diff.x * viewScale.getRotationScale();

        currView.setDegSpinRotation(degSpinDiff + degStartDragSpin);
    }

    public void mouseReleased(MouseEvent mouseEvent) {

        if (isDragging) {

            if (mouseEvent.isButtonDown(MouseEvent.BUTTON1)) {

                if (rotatingMode == RotatingMode.DUAL_AXIS || rotatingMode == RotatingMode.BIAXIAL || rotatingMode == RotatingMode.SPIN) {

                    endDragRotate(mouseEvent);
                }
            }
        }
    }

    private void endDragRotate(MouseEvent mouseEvent) {

        onDragRotate(mouseEvent);

        isDragging = false;
    }

    public void mouseWheelMoved(MouseEvent mouseEvent) {

        if (type == perspective) {

            if (mouseEvent.isShiftDown()) {
//                System.out.println("down " + mouseEvent.getRotation()[1]);
                currView.setRadius(currView.getRadius() + mouseEvent.getRotation()[0] * viewScale.getLargeRadiusDelta());

            } else {
//                System.out.println("up");
                currView.setRadius(currView.getRadius() + mouseEvent.getRotation()[1] * viewScale.getSmallRadiusDelta());
            }
        } else {

            float factor;

            if (mouseEvent.isShiftDown()) {

                factor = mouseEvent.getRotation()[0] / 10;

            } else {

                factor = mouseEvent.getRotation()[1] / 5;
            }
            scale = scale + scale * factor;

            scale = Jglm.clamp(scale, 0.01f, 5f);

            System.out.println("scale: " + scale);
        }
    }

    public float getScale() {
        return scale;
    }

    public enum RotatingMode {

        SPIN,
        BIAXIAL,
        DUAL_AXIS
    }

    public enum Projection {

        orthographic,
        perspective;
    }
}
