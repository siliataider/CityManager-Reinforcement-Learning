import requests
import time
import os

def get_recent_deploy_id(service_id, api_key):
    url = f"https://api.render.com/v1/services/{service_id}/deploys"
    headers = {"Authorization": f"Bearer {api_key}"}
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        deploys = response.json()
        if deploys:
            return deploys[0]['deploy']['id']
    return None

def check_deployment_status(service_id, deploy_id, api_key):
    url = f"https://api.render.com/v1/services/{service_id}/deploys/{deploy_id}"
    headers = {"Authorization": f"Bearer {api_key}"}
    timeout = 1800  # Timeout of 30min?
    start_time = time.time()

    while time.time() - start_time < timeout:
        response = requests.get(url, headers=headers)
        if response.status_code == 200:
            deploy_status = response.json().get('status')
            print(f"Current deployment status: {deploy_status}")

            # Check if the status is one of the final states
            if deploy_status in ["live", "deactivated", "build_failed", "update_failed", "canceled"]:
                print(f"Deployment reached final state: {deploy_status}.")
                return deploy_status == "live"

        else:
            print("Failed to get deployment status.")

        time.sleep(10)  # Wait for 30 seconds before checking again

    return False

if __name__ == "__main__":
    SERVICE_ID = os.environ.get("SERVICE_ID")
    RENDER_API_KEY = os.environ.get("RENDER_API_KEY")
    deploy_id = get_recent_deploy_id(SERVICE_ID, RENDER_API_KEY)
    if deploy_id:
        success = check_deployment_status(SERVICE_ID, deploy_id, RENDER_API_KEY)
        exit(0 if success else 1)
    else:
        print("Failed to retrieve deploy ID.")
        exit(1)
