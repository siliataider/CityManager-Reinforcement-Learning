import { createSlice } from '@reduxjs/toolkit';

export const drawSlice = createSlice({
  name: 'draw',
  initialState: {
    agents : [],
    buildings : [],
  },
  reducers: {
    setAgents: (state, action) =>{
        state.agents = action.payload;
    },

    addBuildings: (state, action) =>{
        state.buildings.push( action.payload );
    },
    
  },
});

export const { setAgents, addBuildings} = drawSlice.actions;

export default drawSlice.reducer;