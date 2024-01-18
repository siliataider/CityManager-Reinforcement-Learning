import pytest
import asyncio
import websockets

def test_one_plus_one_equals_two():
    assert 1 + 1 == 2


# @pytest.mark.asyncio
# async def test_server_connection():
#     try:
#         # Attempt to connect to the WebSocket server
#         async with websockets.connect("wss://citymanagerpython.onrender.com") as websocket:
#             # If the connection is successful, pass the test
#             pass
#     except Exception as e:
#         # If the connection fails, fail the test
#         pytest.fail(f"WebSocket connection failed: {e}")