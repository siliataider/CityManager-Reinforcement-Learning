import { configureStore } from '@reduxjs/toolkit';

import mouseReducer from './assets/mouse/mouseSlice';
import drawsReducer from './assets/canvas/drawsSlice';


export default configureStore({
     reducer: {
        mouse : mouseReducer,
        draw : drawsReducer,        
        },
    })