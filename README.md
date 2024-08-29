# KMMoveAssist

KMMoveAssist is a Minecraft Spigot plugin that enhances player movement by allowing configurable distance-based teleportation using an item.

## Features

- **Right-Click to Move:**
  - Hold an item (default is a clock) and right-click to move in the direction you're facing.
  - The default movement distance is 20 blocks.
  - If the destination is blocked, you'll be teleported to a safe location above the block.
  - Movement won't occur below the world’s minimum height.

- **Left-Click to Change Distance:**
  - Hold an item (default is a clock) and left-click to cycle through different movement distances.
  - Available distances are 20, 40, 80, and 160 blocks.

- **Configurable Item:**
  - The item used for movement can be changed in the `config.yml` file by setting the `move-item` value.

## Permissions

You can configure the following permissions. By default, both permissions are set to OP only.

- `kmmoveassist.move`: Grants permission to move by right-click.
- `kmmoveassist.setdistance`: Grants permission to change the movement distance by left-click.

## Installation

1. Download the KMMoveAssist plugin `.jar` file.
2. Place the `.jar` file in your server's `plugins` directory.
3. Restart your server.

## Configuration

Edit the `config.yml` file in the plugin directory to change the item used for movement:

```yaml
move-item: clock
```

## Compatibility

- Minecraft Java Edition 1.21
- Spigot Server

## License

This plugin is licensed under the [MIT License](LICENSE).

## Author

- **Name:** Komnazsk
- **Twitter (X):** [@komanazu](https://x.com/komanazu)