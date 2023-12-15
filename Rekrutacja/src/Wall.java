import java.util.*;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}

public class Wall implements Structure{
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color){
        for(Block block : blocks){
            if(block instanceof CompositeBlock){
                List<Block> innerBlocks = ((CompositeBlock) block).getBlocks();
                for(Block b : innerBlocks){
                    if(b.getColor().equals(color)){
                        return Optional.of(b);
                    }
                }
            }else{
                 if(block.getColor().equals(color)){
                        return Optional.of(block);
                    }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material){
        List<Block> matchingBlocks = new ArrayList<>();
        for(Block block : blocks){
            if(block instanceof CompositeBlock){
                List<Block> innerBlocks = ((CompositeBlock) block).getBlocks();
                for (Block innerBlock : innerBlocks) {
                    if (innerBlock.getMaterial().equals(material)) {
                        matchingBlocks.add(innerBlock);
                    }
                }
            }else{
                if(block.getMaterial().equals(material)){
                    matchingBlocks.add(block);
                }
            }
        }
        return matchingBlocks;
    }

    @Override
    public int count(){
        int count = 0;
        for(Block block : blocks){
            if(block instanceof CompositeBlock){
                count += ((CompositeBlock) block).getBlocks().size();
            }else{
                count ++;
            }
        }
        return count;
    }
}