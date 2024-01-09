import { createSlice } from '@reduxjs/toolkit';

export const mouseSlice = createSlice({
  name: 'mouse',
  initialState: {
    isDragging: false,
    cursorObject: <></>,

  },
  reducers: {
    switchMouseSate: (state, action) => {
        state.isDragging = !state.isDragging;
    },


    setCursorObject: (state, action) =>{
        state.cursorObject = action.payload;
    }

  },
});

export const { switchMouseSate, setCursorObject } = mouseSlice.actions;

export default mouseSlice.reducer;