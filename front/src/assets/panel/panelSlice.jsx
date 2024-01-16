import { createSlice } from '@reduxjs/toolkit';

export const stateSlice = createSlice({
  name: 'state',
  initialState: {
    isSimulationRuning : false,
  },
  reducers: {
    setSimulationState: (state, action)=>{
        state.isSimulationRuning = action.payload;
    },

  },
});

export const { setAgents, addBuildings} = stateSlice.actions;

export default stateSlice.reducer;