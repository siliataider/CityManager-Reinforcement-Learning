// from https://stackoverflow.com/questions/70317280/remove-last-drawn-object-from-canvas

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
  function getXYCoords (ctx, building, {h = 0, w = 0, centered = true} = {}) {
    const rect = ctx.canvas.getBoundingClientRect();
    const scaleX = ctx.canvas.width / rect.width;
    const scaleY = ctx.canvas.height / rect.height;
    const x = (building.x - rect.left - (centered ? (w / 2) : 0)) * scaleX;
    const y = (building.y - rect.top - (centered ? (h / 2) : 0)) * scaleY;
    return [x, y];
  }

export function drawBuildings(buildings, canvasRef){
    for (const build of buildings){

      const ctx = canvasRef.current?.getContext('2d') ?? null;
      assertIsContext(ctx);
      // Use the helper function to get XY coordinates:
      const [x, y] = getXYCoords(ctx, build, {w:build.size, h:build.size});
      
      const lineWidth = 3;
      const strokeRectOpts = {
        lineWidth,
        strokeStyle: build.color,
        dimensions: [x, y, build.size, build.size],
      };
  
      // Finally, draw the rectangle stroke:
      drawRect(ctx, strokeRectOpts);
    }

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
   * Draw the actual rectangle outline.
   */
    function drawCircle (ctx, options) {
        ctx.beginPath();
        ctx.strokeStyle = options.strokeStyle;

        ctx.arc(options.x, options.y, options.radius, options.startAngle,options.endAngle);

        ctx.stroke();
    }



  export function drawAgents(agents, canvasRef){
    for (const agent of agents){
        const ctx = canvasRef.current?.getContext('2d') ?? null;
        assertIsContext(ctx);

        const [x, y] = getXYCoords(ctx, agent, {w:agent.size, h:agent.size, centered:false})

        const options = {
            x:x,
            y:y,
            radius:agent.size/2,
            startAngle:0,
            endAngle:Math.PI  *2,

            strokeStyle: agent.color,
        };

        drawCircle(ctx,options)
    }
    
  }





  /**
   * Clear the canvas
   */
  export function clearCanavas(canvasRef){
    const ctx = canvasRef.current?.getContext('2d') ?? null;
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  }