// from https://stackoverflow.com/questions/70317280/remove-last-drawn-object-from-canvas


import { useDispatch } from "react-redux";
import { setCursorObject } from "../mouse/mouseSlice";

  /** For making sure the context isn't `null` when trying to access it */
  function assertIsContext (ctx) {
    if (!ctx) throw new Error('Canvas context not found');
  }
  
  /** 
   * Calculate left (x), and top (y) coordinates from a mouse click event
   * on a `<canvas>` element. If `centered` is `true` (default), the center of
   * the reactangle will be at the mouse click position, but if `centered` is
   * `false`, the top left corner of the rect will be at the click position
   */
  function getXYCoords (ev, {h = 0, w = 0, centered = true} = {}) {
    const ctx = ev.target.getContext('2d');
    assertIsContext(ctx);
    const rect = ctx.canvas.getBoundingClientRect();
    const scaleX = ctx.canvas.width / rect.width;
    const scaleY = ctx.canvas.height / rect.height;
    const x = (ev.clientX - rect.left - (centered ? (w / 2) : 0)) * scaleX;
    const y = (ev.clientY - rect.top - (centered ? (h / 2) : 0)) * scaleY;
    return [x, y];
  }
  
  /** 
   * Draw the actual rectangle outline.
   */
  function drawRect (ctx, options) {
    ctx.lineWidth = options.lineWidth;
    ctx.strokeStyle = options.strokeStyle;
    ctx.strokeRect(...options.dimensions);
  }

  /**
   * Clear the canvas
   */
  function clearCanavas(ctx){
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  }
  
  
  /** Example component for this demo */
  function MapCanvas () {

    const dispatch = useDispatch();

    const squareSize = 50;
    const canvasSize = 500;


    // This is where all the magic happens:
    const handleClick = (ev) => {
      const ctx = ev.target.getContext('2d');
      assertIsContext(ctx);
  
      // Use the helper function to get XY coordinates:
      const [x, y] = getXYCoords(ev, {w:squareSize, h:squareSize});
  
      // Again, these are static in your question, but could be in React state:
      const lineWidth = 3;
      const strokeRectOpts = {
        lineWidth,
        strokeStyle: 'red',
        dimensions: [x, y, squareSize, squareSize],
      };
  
      
      // Finally, draw the rectangle stroke:
      drawRect(ctx, strokeRectOpts);

      dispatch(setCursorObject(<></>))

    };
  
    return (
      <div style={{border: '1px solid black', display: 'inline-block'}}>
        <canvas
          onClick={handleClick}
          width= {""+canvasSize}
          height={""+canvasSize}
        ></canvas>
      </div>
    );
  }

export default MapCanvas;