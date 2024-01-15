import { configureStore } from '@reduxjs/toolkit';

import mouseReducer from './assets/mouse/mouseSlice';
import drawsReducer from './assets/canvas/drawSlice';
import socketReducer from './assets/socket/socketSlice';


export default configureStore({
     reducer: {
        mouse : mouseReducer,
        draw : drawsReducer,     
        socket : socketReducer,
        },
    })