/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glutil;

import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL2ES3.GL_UNIFORM_BUFFER;
import static com.jogamp.opengl.GL2ES3.GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.GLBuffers;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author elect
 */
public class UniformBlockArray {

    public ByteBuffer storage;
    public int blockOffset;
    private IntBuffer bufferName = GLBuffers.newDirectIntBuffer(1);

    public UniformBlockArray(GL3 gl3, int blockSize, int arrayCount) {

        IntBuffer uniformBufferAlignSize = GLBuffers.newDirectIntBuffer(1);
        gl3.glGetIntegerv(GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT, uniformBufferAlignSize);

        blockOffset = blockSize;
        blockOffset += uniformBufferAlignSize.get(0) - (blockOffset % uniformBufferAlignSize.get(0));

        int sizeMaterialUniformBuffer = blockOffset * arrayCount;

        storage = GLBuffers.newDirectByteBuffer(sizeMaterialUniformBuffer);

        BufferUtils.destroyDirectBuffer(uniformBufferAlignSize);
    }

    public void createBufferObject(GL3 gl3) {
        gl3.glGenBuffers(1, bufferName);
        gl3.glBindBuffer(GL_UNIFORM_BUFFER, bufferName.get(0));
        gl3.glBufferData(GL_UNIFORM_BUFFER, storage.capacity(), storage, GL_STATIC_DRAW);
        gl3.glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }
}
