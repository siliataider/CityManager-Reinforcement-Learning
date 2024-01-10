import { createSlice } from '@reduxjs/toolkit';

export const mouseSlice = createSlice({
  name: 'mouse',
  initialState: {
    isDragging: false,
    cursorObject: <></>,
    buildingType: null,
    color: null,
  },
  reducers: {
    switchMouseSate: (state, action) => {
        state.isDragging = !state.isDragging;
    },

    setCursorObject: (state, action) =>{
        state.cursorObject = action.payload;
    },

    setBuildingType:(state, action) =>{
      state.buildingType = action.payload[0];
      state.color = action.payload[1];
    },

  },
});

export const { switchMouseSate, setCursorObject, setBuildingType } = mouseSlice.actions;

export default mouseSlice.reducer;