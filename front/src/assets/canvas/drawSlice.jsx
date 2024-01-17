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
        console.log(state.buildings)
    },

    clearMapObjects: (state) => {
      state.buildings = []
      state.agents = []
    },
    
  },
});

export const { setAgents, addBuildings, clearMapObjects} = drawSlice.actions;

export default drawSlice.reducer;