import { Response } from 'express'
import { db } from './config/firebase'

type EntryType = {
  title: string,
  text: string,
  coverImageUrl: string
}

type Request = {
  body: EntryType,
  params: { userId: string, entryId: string }
}

const addEntry = async (req: Request, res: Response) => {
//   const { body: { title, text, coverImageUrl }, params: { userId } } = req
const { body: { title, text }} = req

  try {
    const entry = db.collection('entries').doc()
    const entryObject = {
      id: entry.id,
      title,
      text,
    //   userId,
    //   coverImageUrl: coverImageUrl || ''
    }
    entry.set(entryObject)
    res.status(200).send({
      status: 'success',
      message: 'entry added successfully',
      data: entryObject
    })
  } catch(error: any) { res.status(500).json(error.message) }
}

const getAllEntries = async (req: Request, res: Response) => {
  const allEntries: EntryType[] = []

  try {
    const querySnapshot = await db.collection('entries').get()
    querySnapshot.forEach((doc: { data: () => any; }) => allEntries.push(doc.data()))
    return res.status(200).json(allEntries)
  } catch(error: any) { return res.status(500).json(error.message) }
}

const updateEntry = async (req: Request, res: Response) => {
  const { body: { text, coverImageUrl }, params: { entryId } } = req

  try {
    const entry = db.collection('entries').doc(entryId)
    const currentData = (await entry.get()).data() || {}

    const entryObject = {
      imageUrl: coverImageUrl || currentData.coverImageUrl,
      text: text || currentData.text,
    }

    await entry.set(entryObject).catch(error => {
      return res.status(400).json({
        status: 'error',
        message: error.message
      })
    })

    return res.status(200).json({
      status: 'success',
      message: 'entry updated successfully',
      data: entryObject
    })
  }
  catch(error: any) { return res.status(500).json(error.message) }
}

const deleteEntry = async (req: Request, res: Response) => {
  const { entryId } = req.params

  try {
    const entry = db.collection('entries').doc(entryId)

    await entry.delete().catch(error => {
      return res.status(400).json({
        status: 'error',
        message: error.message
      })
    })

    return res.status(200).json({
      status: 'success',
      message: 'entry deleted successfully',
    })
  }
  catch(error: any) { return res.status(500).json(error.message) }
}

export { addEntry, getAllEntries, updateEntry, deleteEntry }