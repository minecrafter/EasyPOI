package today.crafting.easypoi;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class POI {
    private final String id = "User";
    private final int x;
    private final int y;
    private final int z;
    private final UUID owner;
    private String ownerUsername;
    private final String description;
}
