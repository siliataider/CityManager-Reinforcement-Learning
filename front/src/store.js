import { configureStore } from '@reduxjs/toolkit';

import mouseReducer from './assets/mouse/mouseSlice';


export default configureStore({
     reducer: {
        mouse : mouseReducer,
        },
    })