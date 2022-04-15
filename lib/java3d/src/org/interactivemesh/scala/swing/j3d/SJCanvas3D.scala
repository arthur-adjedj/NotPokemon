package org.interactivemesh.scala.swing.j3d
/*

License (following the Scala license)

Copyright (c) 2010-2011
August Lammersdorf, InteractiveMesh e.K.
Kolomanstrasse 2a, 85737 Ismaning, Germany / Munich Area
www.InteractiveMesh.com/org
 
All rights reserved.

Permission to use, copy, modify, and distribute this software in source
or binary form for any purpose with or without fee is hereby granted,
provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its contributors
    may be used to endorse or promote products derived from this
    software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

*/

// Java
import java.awt.{Graphics2D, GraphicsDevice, GraphicsEnvironment}
import java.util.concurrent.TimeUnit

// Java 3D
import javax.media.j3d.GraphicsConfigTemplate3D

/** The class SJCanvas3D provides a lightweight Scala Swing component 
 *  that Java 3D can render into.
 *  
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.3 - 2011/09/19
 */
class SJCanvas3D(
    device: GraphicsDevice,
    template: GraphicsConfigTemplate3D) 
      extends SJCanvas3DAbstract(device, template) {
	
  def this(template: GraphicsConfigTemplate3D) = this(  
    GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice,
    template
  )

  def this(device: GraphicsDevice) = this(device, new GraphicsConfigTemplate3D)

  def this() = this(
	GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice,
	new GraphicsConfigTemplate3D
  )
  
  /** Creates a new off-screen buffer of the given size. This method is called 
   *  internally whenever this panel is added to a parent or is resized. 
   *  <p>
   *  Subclasses should call and/or override this method according to its
   *  individual needs. In case of overriding calling
   *  <code>super.createOffScreenBuffer(canvasWidth, canvasHeight)</code> 
   *  has to be the last thing to do.</p>
   *  @param width the width of the off-screen buffers to create
   *  @param height the height of the off-screen buffers to create
   */
  override protected def createOffScreenBuffer(width: Int, height: Int) {   
	
    super.createOffScreenBuffer(width, height)
  }

  /**
   * Callback used to allow an overriding subclass to execute individual code
   * when a new off-screen buffer was created. 
   * <p>
   * This method is called internally by the event-dispatching thread (EDT)
   * and should not be called by applications. 
   * </p>
   */
  override protected def offScreenBufferCreated() {
	  
  }

  /**
   * Callback used to allow an overriding subclass to execute individual code
   * when the off-screen buffer was copied.
   * <p>
   * This method is called internally by the event-dispatching thread (EDT)
   * and should not be called by applications. 
   * </p>
   */
  override protected def offScreenBufferCopied() {
	  
  }

  // Flip and draw 
  override protected def paintComponent(g2D: Graphics2D) {
    peer match {
      case peer: CanvasSuperMixin => super.paintComponent(g2D)
            
        // Draw & flip offscreen buffer        
        if (isReadyForDrawing) {          
                      
          var isLocked: Boolean = false

          try {
            // Avoids deadlock when J3D-Renderer thread acquires the lock 
            // in 'postRender' but don't call 'postSwap' due to J3D internal states
            isLocked = imageAccessLock.tryLock(50, TimeUnit.MILLISECONDS)
                
            g2D.drawImage(paintImage,
                        // destination in g2d: flip lowerY and upperY 
                        // dx1    dy1          dx2         dy2
                            0, imageHeight, imageWidth, 0,    
                        // source: the bottom part of the scene image
                        // sx1    sy1          sx2         sy2
                            0, 0,           imageWidth, imageHeight, null)
    
             // Release J3D-Renderer thread
             isImageDrawn = true
             if (isLocked)
               imagePaintCondition.signal              
           }         
           finally {  
        	 if (isLocked)
               imageAccessLock.unlock
           }
        }
      
      case _ => {}
    }
  }

}
