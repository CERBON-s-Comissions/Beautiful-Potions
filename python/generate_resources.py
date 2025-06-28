import os
import json
import shutil

def generate_resources():
    # Base path for the to_generate folder
    to_generate_path = os.path.join(os.path.dirname(__file__), "to_generate")

    # Check if to_generate folder exists
    if not os.path.exists(to_generate_path):
        print(f"Error: '{to_generate_path}' directory not found.")
        return

    # Process each mod ID folder
    for mod_id in os.listdir(to_generate_path):
        mod_path = os.path.join(to_generate_path, mod_id)

        # Skip if not a directory
        if not os.path.isdir(mod_path):
            continue

        print(f"Processing mod: {mod_id}")

        # Process each potion ID folder
        for potion_id in os.listdir(mod_path):
            potion_path = os.path.join(mod_path, potion_id)

            # Skip if not a directory
            if not os.path.isdir(potion_path):
                continue

            print(f"  Processing potion: {potion_id}")

            # Create destination directories
            textures_dest_dir = os.path.join("assets", mod_id, "textures", "item", "potion", potion_id)
            models_dest_dir = os.path.join("assets", mod_id, "models", "item", "potion", potion_id)

            # Create directories if they don't exist
            os.makedirs(textures_dest_dir, exist_ok=True)
            os.makedirs(models_dest_dir, exist_ok=True)

            # Process each texture file
            for texture_file in os.listdir(potion_path):
                if not texture_file.endswith(".png"):
                    continue

                texture_name = os.path.splitext(texture_file)[0]  # Remove extension
                texture_src = os.path.join(potion_path, texture_file)
                texture_dest = os.path.join(textures_dest_dir, texture_file)

                # Copy texture file
                shutil.copy2(texture_src, texture_dest)
                print(f"    Copied texture: {texture_file} to {textures_dest_dir}")

                # Generate JSON model file
                json_content = {
                    "parent": "minecraft:item/generated",
                    "textures": {
                        "layer0": f"{mod_id}:item/potion/{potion_id}/{texture_name}"
                    }
                }

                json_file_path = os.path.join(models_dest_dir, f"{texture_name}.json")

                with open(json_file_path, 'w') as json_file:
                    json.dump(json_content, json_file, indent=4)

                print(f"    Generated JSON: {json_file_path}")

    print("\nResource generation complete!")

if __name__ == "__main__":
    generate_resources()