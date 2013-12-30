package agu.bitmap.png.chunks;

import agu.bitmap.png.ImageInfo;
import agu.bitmap.png.PngHelperInternal;
import agu.bitmap.png.PngjException;

/**
 * hIST chunk.
 * <p>
 * see http://www.w3.org/TR/PNG/#11hIST <br>
 * only for palette images
 */
public class PngChunkHIST extends PngChunkSingle {
	public final static String ID = ChunkHelper.hIST;

	private int[] hist = new int[0]; // should have same lenght as palette

	public PngChunkHIST(ImageInfo info) {
		super(ID, info);
	}

	@Override
	public ChunkOrderingConstraint getOrderingConstraint() {
		return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
	}

	@Override
	public void parseFromRaw(ChunkRaw c) {
		if (!imgInfo.indexed)
			throw new PngjException("only indexed images accept a HIST chunk");
		int nentries = c.data.length / 2;
		hist = new int[nentries];
		for (int i = 0; i < hist.length; i++) {
			hist[i] = PngHelperInternal.readInt2fromBytes(c.data, i * 2);
		}
	}

	@Override
	public PngChunk cloneForWrite(ImageInfo imgInfo) {
		PngChunkHIST other = new PngChunkHIST(imgInfo);
		other.hist = new int[hist.length];
		System.arraycopy(hist, 0, hist, 0, other.hist.length);
		return other;
	}

	public int[] getHist() {
		return hist;
	}

	public void setHist(int[] hist) {
		this.hist = hist;
	}

}