import { createSlice } from '@reduxjs/toolkit';

export const drawSlice = createSlice({
  name: 'draw',
  initialState: {
    agents : [],
    buildings : [],
    current_agent_id : null,
  },
  reducers: {
    setAgents: (state, action) =>{
        state.agents = action.payload;
    },

    addBuildings: (state, action) =>{
        state.buildings.push( action.payload );
    },

    clearMapObjects: (state) => {
      state.buildings = [];
      state.agents = [];
    },

    setCurrentAgentId: (state, action) => {
      state.current_agent_id = action.payload;
    },
    
  },
});

export const { setAgents, addBuildings, clearMapObjects, setCurrentAgentId} = drawSlice.actions;

export default drawSlice.reducer;